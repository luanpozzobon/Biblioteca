<h1 align="center">:file_cabinet: Getlib</h1>

## :memo: Descrição
Biblioteca digital para aluguel e compra de livros.
* O sistema apenas simula uma biblioteca, não é possível comprar ou alugar nenhum livro.
* Todos os livros presentes, são reais, e foram adicionados a partir da API do Google Books, título e gênero são dados reais, os outros dados são aleatórios.
* Os valores dos livros são valores aleatórios, adicionados em dólar.
* Os valores editados pelos funcionários, também devem ser colocados em dólar.
* Os valores mostrados aos clientes, é convertido para a moeda local, portanto ele pode variar de acordo com a cotação atual.

## :books: Funcionalidades
* <b>Cadastro</b>: Cadastro de novos usuários.
* <b>Criptografia</b>: Salting e hash da senha.
* <b>Autenticação</b>: Login de usuários.
* <b>Listagem</b>: É possível ver todos os livros cadastrados.
* <b>Busca</b>: É possível procura por um livro específico.
* <b>Inserção/Remoção</b>: Funcionários podem inserir e remover livros.
* <b>Aluguel</b>: É possível realizar o alguel de livros (gratuito até 5 dias).
* <b>Devolução</b>: Caso o livro esteja alugado, é possível fazer a devolução.
* <b>Compra</b>: É possível fazer a aquisição permanente de um livro (caso esteja alugado, o valor do aluguel não será cobrado, apenas o valor da compra).
* <b>Troca e devolução</b>: É possível trocar livros, e devolvê-los (em até 7 dias).
* <b>Exclusão de conta</b>: É possível deletar as contas de cliente (caso não haja livros alugados) e funcionários.

## :wrench: Tecnologias utilizadas
* Java 19
* MySQL 5.5.62-0
* Hibernate

### :hammer: API's
* <b>[Zip Code](https://zipcodebase.com/)</b>: API de consulta de zip code (CEP), para auto-preenchimento de endereço.
* <b>[REST Countries](https://restcountries.com/)</b>: API de consulta de informações sobre países, utilizada para obtenção da moeda local.
* <b>[API de Cotações](https://docs.awesomeapi.com.br/api-de-moedas)</b>: API de cotação, utilizada para conversão de valores na moeda local do cliente.

### :hammer: Dependências
* <b>gson 2.10.1</b>: Dependência para leitura de JSON's.
* <b>mysql-connector 8.0.33</b>: Dependência para conexão ao banco de dados MySQL.

## :rocket: Rodando o projeto
Atualmente o projeto não está em execução!

## :soon: Implementação futura

## :handshake: Colaboradores
<table>
    <tr>
        <td align="center">
            <a href="https://github.com/luanpozzobon">
                <img src="https://avatars.githubusercontent.com/u/108753073?v=4" width="100px;" alt="Foto de Luan Pozzobon no Github"/><br>
                <sub>
                    <b>LuanPozzobon</b>
                </sub>
            </a>
        </td>
    </tr>
</table>

## :dart: Status do projeto
Em andamento
