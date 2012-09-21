jbc-framework

JBC - Java Business Controller, � um Framework de Controle da camada de Neg�cio para projetos desenvolvidos em Java, seu principal objetivo � otimizar o tempo de desenvolvimento que � usado para projetar c�digos repetitivos, que por sua vez s�o muito frequentes quando se trata de controle de transa��o e persist�ncia em banco de dados.

O JBC � utilizado somente na camada de neg�cio para projetos desenvolvidos em Java, auxiliando como um utilit�rio para as camadas de Model e Control do modelo de desenvolvimento MVC. Sua fun��o � fazer o controle de transa��es entre as opera��es e realizar o acesso ao banco de dados, sendo atrav�s de persist�ncia ou consultas. A base principal para o seu desenvolvimento, foi a utiliza��o dos conceitos Java de Generics e Reflection, ou seja, programa��o gen�rica.

Para trabalhar com persist�ncia em banco de dados, foi utilizado o Framework Hibernate, que atrav�s dos conceitos de Criteria e HQL, auxiliaram no encapsulamento e na manipula��o dos objetos com a base de dados, como tamb�m tornaram o JBC independente de banco de dados, ou seja, um item deixado para a escolha do desenvolvedor. Por fim, para se conectar com o Database, foi utilizado a API JDBC.

Como o JBC � um Framework de implementa��o da camada de neg�cio, ele pode ser usado para qualquer tipo de implementa��o View do conceito MVC, ou seja, � independente de interface de apresenta��o com o cliente, pode ser utilizado em aplica��es J2SE, J2ME e J2EE, sendo aplica��es Desktop, Web e Mobile.

@autor: Rodrigo Leandro Marconato