# Sistema de Transferência de Dinheiro 💸

Este é um sistema de transferência de dinheiro onde usuários comuns e lojistas podem enviar e receber dinheiro entre si. 
O sistema verifica tipo de usuário, saldo antes da transferência consulta um serviço autorizador externo(mock) antes de finalizar a transferência e envia notificações aos usuários ou lojistas após o recebimento do pagamento.

## Requisitos

- Cadastro de usuários comuns e lojistas com Nome Completo, CPF/CNPJ, e-mail e Senha.
- CPF/CNPJ e e-mails devem ser únicos no sistema.
- Transferência de dinheiro entre usuários comuns e entre usuários comuns e lojistas.
- Validação de saldo antes da transferência.
- Consulta a um serviço autorizador externo antes de finalizar a transferência.
- Notificação de recebimento de pagamento aos usuários ou lojistas.

## Pré-requisitos

- Java 17 ☕
- Spring Boot 3.3.2 🍃
- Banco de Dados (H2, MySQL, etc.) 
- Maven ou Gradle

## Configuração do Projeto

1. Clone o repositório:

```bash
git clone https://github.com/GabrielBressi/pic-pay-simplificado
```
Importe o projeto em sua IDE favorita.

Certifique-se de que as dependências do Maven/Gradle estejam atualizadas.

Configure o banco de dados conforme necessário (consulte application.properties para configurações de banco de dados).

Execute a aplicação Spring Boot.

### Uso
Acesse a API RESTFul conforme descrito na documentação.
Realize o cadastro de usuários comuns e lojistas.
Efetue transferências de dinheiro entre os usuários cadastrados.
Verifique as notificações recebidas após o recebimento do pagamento.
API Endpoints
- /api/users: CRUD de usuários comuns e lojistas.
- /api/transactions: Endpoint para realizar transferências de dinheiro.

### Contribuindo
Contribuições são bem-vindas! Sinta-se à vontade para abrir um PR para melhorias, correções de bugs ou novos recursos.
