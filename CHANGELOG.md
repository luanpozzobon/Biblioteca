# Changelog

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
 