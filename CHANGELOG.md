# Changelog

## [0.1.1] - 2023-08-17

### Added

- Formatação da data para LocalDate a partir de String.
- Validação de tamanho mínimo e unicidade do username.
- Validação de condições mínimas para criação de senha segura.
- Salting e hash da senha.
- Conexão ao banco de dados (Salvar e buscar cliente)

### Changed

- Alterado tipo de dado para a data de nascimento (String -> LocalDate)

## [0.1.0] - 2023-08-14

### Added

- Modelagem das classes de usuário (Customer e Employee), e da classe de Endereço (Address).
- Adicionado classe para requisições HTTP.
- Adicionado consulta automática de endereço a partir do CEP.
- Classe de leitura de entradas do usuário, para consertar bug do Scanner.
 