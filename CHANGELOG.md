# Changelog

## [1.1.2] - 2023-08-31

### Added

- Adicionado objeto da classe `Console` no `InputReader`.

### Changed

- Atualização de dependências: Gson 2.10.1, MySql-Connector 8.0.33.
- A leitura do console é feita pela classe `Console` e quando não for possível, a leitura é feita pelo `Scanner`.

## [1.1.1] - 2023-08-30

### Added

- Adicionado função de formatação do Zip Code, para retirar caracteres especiais.
- Adicionado função de formatação de data, para aceitar diferentes formatos de entrada de data.

## [1.1.0] - 2023-08-29

### Added

- Adicionado função para listar meus livros.
- Adicionado método `findByCustomer` em `OperationDAO`.
- Adicionado método `findRentByCustomerId` em `OperationsDAO`.
- Adicionado recarga de créditos para Clientes.
- Adicionado devolução e troca de livros comprados.
- Adicionado deleção de contas.

### Removed

- Removido método `findRentByBookAndCustomer`.

## [1.0.0] - 2023-08-25

### Added

- Adicionado método `findById` no `EmployeeDAO`.
- Adicionado mensagens de erro, na classe `InputReader`.
- Adicionado método de fechamento do Scanner na classe `InputReader`.
- Adicionado método `updateEmployee` na classe `EmployeeDAO`.
- Adicionado arquivo executável.

### Changed

- Alteradas mensagens de erro nas classes de banco de dados e de API.
- O método `findByAccessCode` agora retorna um objeto apenas com id e o accessCode.
- O método `findByAccessCode` não utiliza mais um construtor passando o ResultSet.

### Removed

- Removido construtor de `Employee` que recebia um ResultSet.

### Fixed

- Alteração de senha do funcionário é realizada a partir do método `updateEmployee`, anteriormente o método tentava criar um novo funcionário.


## [0.2.1] - 2023-08-24

### Added

- Adicionado construtor de `Customer` que recebe `id`, e `username`.
- Adicionado construtor de `Address` que recebe uma string do banco de dados.
- Adicionado métodos `updateCustomer` e `findById` para `Customer`.
- Adicionado classes `Operations` e `OperationsDAO` para compras e aluguel.
- Adicionado lógica de aluguel, devolução e compra de livros.

### Changed

- Método `usernameExists` agora é privado, sendo usado apenas através do `isUsernameValid`.
- Método `findByUsername` agora retorna um objeto com `id` e `username`.
- Tipo de dado do `parentalRating` dos livros agora é `int`.
- Classificação indicativa `Livre` agora recebe é `0`.

### Removed

- Removido construtor de `Customer` que utilizava ResultSet.


## [0.2.0] - 2023-08-22

### Added

- Adicionado classe modelo de livros.
- Adicionado classe de banco de dados para livros.
- Adicionada função para alterar senha do Funcionário.
- Adicionado listagem e busca de livros (funcionários).
- Adicionado edição, inserção e remoção de livros (funcionários).
- Adicionado método de salvamento e atualização de livros.

### Changed

- O método `isPasswordValid` agora utiliza o método `arePasswordEquals` para verificar se as senhas são iguais.

### Removed

- Removido método `passwordMatch` na classe `DataValidator`.


## [0.1.2] - 2023-08-19

### Added

- Cadastro de funcionários.
- Validação de idade.
- Autenticação de clientes e funcionários.
- Consulta automática da moeda local.

### Changed

- Alterado estrutura de "telas".


## [0.1.1] - 2023-08-17

### Added

- Formatação da data para LocalDate a partir de String.
- Validação de tamanho mínimo e unicidade do username.
- Validação de condições mínimas para criação de senha segura.
- Salting e hash da senha.
- Conexão ao banco de dados (Salvar e buscar cliente).

### Changed

- Alterado tipo de dado para a data de nascimento (String -> LocalDate).


## [0.1.0] - 2023-08-14

### Added

- Modelagem das classes de usuário (Customer e Employee), e da classe de Endereço (Address).
- Adicionado classe para requisições HTTP.
- Adicionado consulta automática de endereço a partir do CEP.
- Classe de leitura de entradas do usuário, para consertar bug do Scanner.
 