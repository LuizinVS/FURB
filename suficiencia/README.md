# RestAPIFurb

API REST JSON para cadastro de equipamentos, feita com Java 21, Spring Boot, JPA/PostgreSQL, Bean Validation, Spring Security, JWT e Swagger.

## 1. Requisitos

- Java 21 (`java -version`)
- Maven 3.9+ (`mvn -version`)
- PostgreSQL 14+

## 2. Banco PostgreSQL

Entre no `psql` como administrador e crie o banco (as tabelas serão criadas pelo Hibernate):

```sql
CREATE DATABASE restapifurb;
```

Por padrão, a aplicação usa `jdbc:postgresql://localhost:5432/restapifurb`, usuário `postgres` e senha `postgres`. Para outra configuração, use variáveis de ambiente.

## 3. Variáveis de ambiente

PowerShell (válidas para a janela atual):

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/restapifurb"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="sua_senha"
$env:JWT_SECRET="uma-chave-aleatoria-com-pelo-menos-32-caracteres"
```

`JWT_EXPIRATION_MS` é opcional e vale 3.600.000 ms (uma hora) por padrão. Não versione senhas nem segredos reais. Os defaults em `application.properties` são apenas para desenvolvimento local.

## 4. Iniciar

Na raiz do projeto:

```powershell
mvn clean spring-boot:run
```

Ou gere e execute o JAR:

```powershell
mvn clean package
java -jar target/restapifurb-0.0.1-SNAPSHOT.jar
```

URL base: `http://localhost:8080/RestAPIFurb`

Na primeira execução em um banco vazio são cadastrados os três tipos, os três equipamentos solicitados e o usuário `admin`/`admin123`. A senha é persistida como hash BCrypt e os dados não são duplicados a cada inicialização.

## 5. Swagger

Abra `http://localhost:8080/RestAPIFurb/swagger-ui.html`. Faça login, copie apenas o valor de `token`, clique em **Authorize** e cole o token. O Swagger acrescenta `Bearer` automaticamente.

## 6. Endpoints e autenticação

| Método | Endpoint | JWT | Resultado |
|---|---|---:|---|
| POST | `/auth/login` | Não | Gera token |
| GET | `/equipamentos` | Não | Lista sob a propriedade raiz `equipamentos` |
| GET | `/equipamentos/{id}` | Não | Busca por ID |
| POST | `/equipamentos` | Sim | Cria e retorna `201 Created` |
| PUT | `/equipamentos/{id}` | Sim | Atualização parcial |
| DELETE | `/equipamentos/{id}` | Sim | Remove o item |

## 7. Testes no Postman

Defina uma variável de coleção `baseUrl` com `http://localhost:8080/RestAPIFurb`.

### Login

`POST {{baseUrl}}/auth/login`, aba **Body > raw > JSON**:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Copie o campo `token` da resposta. Nos endpoints protegidos, use **Authorization > Bearer Token** e cole o valor. Alternativamente, envie o header `Authorization: Bearer SEU_TOKEN`.

### Listar e buscar

```text
GET {{baseUrl}}/equipamentos
GET {{baseUrl}}/equipamentos/1
```

A lista possui este formato:

```json
{
  "equipamentos": [
    { "id": 1, "nome": "Notebook Dell", "tipo": { "id": 1, "nome": "Computador" } }
  ]
}
```

### Criar (JWT obrigatório)

`POST {{baseUrl}}/equipamentos`:

```json
{
  "nome": "Imp HP",
  "tipo": { "id": 3, "nome": "Impressora" }
}
```

O servidor usa o `tipo.id` para associar um tipo já existente; o `nome` dentro de `tipo` é aceito no JSON, mas o nome oficial vem do banco.

### Atualizar parcialmente (JWT obrigatório)

`PUT {{baseUrl}}/equipamentos/1` para mudar somente o nome:

```json
{ "nome": "Novo nome" }
```

Ou somente o tipo:

```json
{ "tipo": { "id": 2 } }
```

Campos ausentes permanecem como estavam. Um corpo vazio, nome em branco ou tipo inexistente produz erro JSON apropriado.

### Remover (JWT obrigatório)

`DELETE {{baseUrl}}/equipamentos/1`. Resposta:

```json
{ "success": { "text": "equipamento removido" } }
```

## 8. Getters e setters

Todas as entidades e DTOs possuem getters e setters explícitos, sem depender de Lombok. Isso permite que Jackson transforme o JSON do Postman em objetos Java e serialize as respostas. No Postman você não chama getters/setters: basta enviar os JSONs acima com `Content-Type: application/json`.

## 9. Arquitetura

```text
src/main/java/br/furb/restapifurb
├── config       Security, OpenAPI e carga inicial
├── controller   Recebe HTTP, valida e chama services
├── dto          Objetos de entrada e saída da API
├── exception    Exceções e tratamento global em JSON
├── model        Entidades JPA Tipo, Equipamento e Usuario
├── repository   Interfaces JpaRepository
├── security     Filtro, geração/validação JWT e UserDetails
└── service      Regras de negócio e transações
```

Decisões importantes para explicar: `ManyToOne` representa que vários equipamentos podem ter o mesmo tipo; DTOs evitam expor diretamente entidades; o DTO específico de PUT tem campos opcionais e o service altera somente os campos presentes; JWT torna a API stateless; BCrypt impede guardar senha em texto puro; `@RestControllerAdvice` centraliza erros; e `ddl-auto=update` cria/atualiza tabelas no PostgreSQL.

## 10. Verificação automatizada

```powershell
mvn test
mvn package
```
