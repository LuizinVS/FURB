# RestAPIFurb

API REST simples para avaliação de Programação Web II, com Spring Boot, JPA/Hibernate, PostgreSQL, JWT e Swagger.

## 1. Requisitos

- Java 21
- Maven
- PostgreSQL

## 2. Criar e configurar o PostgreSQL

1. Crie um banco chamado `restapifurb`.
2. O projeto usa o usuário `postgres` e a senha `postgres` por padrão.
3. Se quiser, altere as variáveis de ambiente abaixo.

Exemplo no PostgreSQL:

```sql
CREATE DATABASE restapifurb;
```

## 3. Variáveis de ambiente

O projeto já possui valores padrão para desenvolvimento, mas você pode sobrescrever:

```bash
set DB_PASSWORD=postgres
set JWT_SECRET=meu-segredo-local
```

## 4. Como iniciar o projeto

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080/RestAPIFurb/
```

## 5. URL base

```text
http://localhost:8080/RestAPIFurb
```

## 6. Swagger

A documentação Swagger estará disponível em:

```text
http://localhost:8080/RestAPIFurb/swagger-ui.html
```

## 7. Como fazer login

Envie uma requisição para:

```http
POST /auth/login
```

Body:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Resposta:

```json
{
  "token": "..."
}
```

## 8. Como copiar o JWT

Copie o valor retornado no campo `token` e envie no header:

```http
Authorization: Bearer SEU_TOKEN
```

## 9. Como chamar endpoints protegidos

Exemplo com `curl`:

```bash
curl -X POST http://localhost:8080/RestAPIFurb/equipamentos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"nome":"Impressora HP","tipo":{"id":3,"nome":"Impressora"}}'
```

## 10. Exemplos de JSON

### GET /equipamentos

```json
{
  "equipamentos": [
    {
      "id": 1,
      "nome": "Notebook Dell",
      "tipo": {
        "id": 1,
        "nome": "Computador"
      }
    }
  ]
}
```

### POST /equipamentos

```json
{
  "nome": "Imp HP",
  "tipo": {
    "id": 3,
    "nome": "Impressora"
  }
}
```

### PUT /equipamentos/{id} (atualização parcial)

```json
{
  "nome": "Novo nome"
}
```

## 11. Arquitetura/pastas do projeto

```text
src/main/java/br/furb/restapifurb
├── config
├── controller
├── dto
├── exception
├── model
├── repository
├── security
└── service
```

## 12. Exemplos de chamadas manuais

### Listar equipamentos

```bash
curl http://localhost:8080/RestAPIFurb/equipamentos
```

### Buscar equipamento por ID

```bash
curl http://localhost:8080/RestAPIFurb/equipamentos/1
```

### Login

```bash
curl -X POST http://localhost:8080/RestAPIFurb/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Criar equipamento autenticado

```bash
curl -X POST http://localhost:8080/RestAPIFurb/equipamentos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"nome":"Impressora HP","tipo":{"id":3,"nome":"Impressora"}}'
```

### Atualizar parcialmente autenticado

```bash
curl -X PUT http://localhost:8080/RestAPIFurb/equipamentos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"nome":"Notebook Atualizado"}'
```

### Excluir equipamento autenticado

```bash
curl -X DELETE http://localhost:8080/RestAPIFurb/equipamentos/1 \
  -H "Authorization: Bearer SEU_TOKEN"
```
