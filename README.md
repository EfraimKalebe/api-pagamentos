# Payment API

API REST para recebimento de pagamentos de débitos de pessoas físicas e jurídicas.

## Tecnologias

- Java 11
- Spring Boot 2.7.14
- Spring Data JPA
- H2 Database
- Maven

## Requisitos

- Java 11 ou superior
- Maven 3.6 ou superior

## Executar a Aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## Console H2

Acesse o console do banco de dados H2 em: `http://localhost:8080/h2-console`

Configurações de conexão:
- JDBC URL: `jdbc:h2:mem:paymentdb`
- Username: `sa`
- Password: (deixar em branco)

## Endpoints

### Criar Pagamento

```
POST /api/pagamentos
Content-Type: application/json

{
  "codigoDebito": 123,
  "cpfCnpjPagador": "12345678900",
  "metodoPagamento": "PIX",
  "numeroCartao": "1234567890123456",
  "valorPagamento": 100.50
}
```

Métodos de pagamento aceitos: `BOLETO`, `PIX`, `CARTAO_CREDITO`, `CARTAO_DEBITO`

### Atualizar Status do Pagamento

```
PATCH /api/pagamentos/{id}/status
Content-Type: application/json

{
  "novoStatus": "PROCESSADO_SUCESSO"
}
```

Status disponíveis: `PENDENTE_PROCESSAMENTO`, `PROCESSADO_SUCESSO`, `PROCESSADO_FALHA`

Regras de transição:
- `PENDENTE_PROCESSAMENTO` pode ser alterado para `PROCESSADO_SUCESSO` ou `PROCESSADO_FALHA`
- `PROCESSADO_SUCESSO` não pode ser alterado
- `PROCESSADO_FALHA` só pode ser alterado para `PENDENTE_PROCESSAMENTO`

### Listar Pagamentos

Listar todos os pagamentos:
```
GET /api/pagamentos
```

Filtrar por código do débito:
```
GET /api/pagamentos?codigoDebito=123
```

Filtrar por CPF/CNPJ:
```
GET /api/pagamentos?cpfCnpjPagador=12345678900
```

Filtrar por status:
```
GET /api/pagamentos?status=PENDENTE_PROCESSAMENTO
```

### Excluir Pagamento (Exclusão Lógica)

```
DELETE /api/pagamentos/{id}
```

Apenas pagamentos com status `PENDENTE_PROCESSAMENTO` podem ser excluídos.

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── fadesp/
│   │           └── payment/
│   │               ├── controller/
│   │               ├── dto/
│   │               ├── exception/
│   │               ├── model/
│   │               ├── repository/
│   │               ├── service/
│   │               └── PaymentApiApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## Build

```bash
mvn clean package
```

O arquivo JAR será gerado em `target/payment-api-1.0.0.jar`

## Executar JAR

```bash
java -jar target/payment-api-1.0.0.jar
```

