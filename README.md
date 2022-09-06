# 🛒 Marketplace

[![Versão do Spring Boot](https://img.shields.io/badge/Spring_Booot-v2.7.1-6DB33F)](https://spring.io/projects/spring-boot/)

## 📚 Sobre
Projeto de um microserviço Spring, de um marketplace, que se comunica com outros serviços através do RabbitMQ. 
Desenvolvido em conjunto com [Adamo Maia](https://github.com/yKronoos) na matéria de Sistemas Distribuídos no IF Goiano - Campus Urutaí.

## 📝 O Estudo de Caso

Tem-se um negócio de transporte de produtos, onde o Marketplace é o local por onde os produtos são 
registrados e enviados para a Transportadora, que por sua vez despacha o produto para uma empresa de 
transporte baseada no peso do produto, ou seja, para uma empresa especializada em transporte de 
produtos leves ou pesados.

O sistema completo é mostrado na fugura abaixo.

![Sistema de Transporte drawio](https://user-images.githubusercontent.com/56361775/188547351-ada5d1fe-ee85-4f85-81eb-8fb7afd3c9e2.svg)

O Marketplace envia a mensagem para a transportadora, através de uma fila de mensagens, 
chamada ‘SEND_PRODUCTS’, implementada sobre o RabbitMQ, que transmitirá os dados do produto. 
Marketplace é uma API Rest, que recebe requisições POST de um produto que precisa ser enviado.

Esses dados serão recebidos pela Transportadora, que é um micro serviço consumidor da fila ‘SEND_PRODUCTS’, 
a qual decidirá para qual fila o produto será encaminhado, para que uma empresa de transporte o receba. 
Se o peso for maior que 10 kg, o produto será encaminhado para a fila de produtos pesados (HEAVY_PRODUCTS), 
porém se tiver esse peso ou for mais leve que isso, será enviado para a fila de produtos leves (LIGHT_PRODUCTS).

Implementamos 4 micro serviços de empresas que recebem um produto para fazer o transporte:
- [Gato a Jato](https://github.com/yKronoos/Empresa-Gato-a-Jato) - implementada com node.js, é uma empresa especializada em transporte de produtos pesados;
- [Uno da Firma](https://github.com/yKronoos/Empresa-Uno-da-Firma) - implementada com Spring Boot, é uma empresa especializada em transporte de produtos leves;
- [Sonic](https://github.com/yKronoos/Empresa-Sonic) - implementada com Spring Boot, é uma empresa especializada em transporte de produtos pesados;
- [TURBO](https://github.com/yKronoos/Empresa-Turbo) - implementada com node.js, é uma empresa especializada em transporte de produtos leves;

Assim, quando uma dessas empresas finaliza o transporte de um produto, esta gera uma resposta sobre a entrega, 
e envia para a fila de resposta da entrega (RESPONSE). A transportadora é um consumidor dessa fila, 
que passa essa resposta para a fila de relatório (REPORT). O Marketplace, por sua vez, é um consumidor
da fila de relatórios, e dessa forma, recebe as informações da entrega do produto

## ⚙️ Outros microserviços do sistema

- [Transportadora](https://github.com/wandersonfelipegp13/shipping-company)
- [Biblioteca](https://github.com/wandersonfelipegp13/transporty-library) java para constantes do RabbitMQ e tipo de dados
