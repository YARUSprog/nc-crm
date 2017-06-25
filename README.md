# nc-crm
Web, single-page application. СRM system for the company. 

Technology Stack and Project Architecture: 
https://docs.google.com/document/d/1WJZPFgQBEWavngPRs_bvx7vtTCTvjBK3QzAl-A2C6k8/edit?usp=sharing

ER-model:  
[ER-діаграма CRM системи(0.8.8).pdf](https://github.com/ncProjectRoot/nc-crm/files/1034114/ER-.CRM.0.8.8.pdf)

Production version - http://nc-project.tk 
1. Customer
- Login: customer0@gmail.com
- password: 123123
2. CSR 
- Login: csr1@gmail.com
- password: 123123
3. PMG 
- Login: pmg1@gmail.com
- password: 123123

General business requirements for a software product

| Business Requirement Number  | Description of business requirements |
| ------------- | ------------- |
| 1  | The system should provide the user with a Web interface as the only way to access and interact with the system.  |
| 2  | The system should allow the user to interact with one of the following roles: -	Administrator, Client, CSR,	Problem Management Group.  |
| 3  | The system should provide user access to a certain functionality, depending on the role.  |
| 4  | The system should provide the user with the role of "Administrator" to create users with the role of "Problem Management Group, CSR, Client." |
| 5  | The system should provide the ability to create and edit a product catalog with the ability to configure the price depending on the region (the area is determined according to the address specified in the initial registration).  |
| 6  | The system should allow the configuration of discounts on existing products in the catalog  |
| 7  | The system should allow the registration of "Clients" with the further storage of information about their account (PIN, address, telephone), and information about their order and the status of their services (Active / Suspended / Deactivated)  |
| 8  | The system should allow the Client to order / pause / disconnect services (tariff packages and additional services).  |
| 9  | The system should inform users via e-mail of important events (for example, tell the Client that the service has been successfully activated / suspended / deactivated, send a message about successful registration, send Clients about new services / tariffs).  |
| 10  | The system should be able to track the lifecycle of orders for CSR.  |
| 11  | The system should allow the creation and processing of complaints from Clients (including "Clients" complaints about system problems, service provision violations).  |
| 12  | The system should send an e-mail notification of the acceptance / change of status / complaint.  |
| 13  | The system should provide an opportunity to compile a report in xls / xlsx format, as well as build graphs showing information for all account / account group orders for a certain period.  |
| 14  | The system should provide an opportunity to compile a report in xls / xlsx format, as well as build charts for the Problem Management Group, with information on problems (appeals and complaints).  |
| 15  | The system should provide the opportunity to create marketing campaigns for Clients of different areas.  |
| 16  |The system should enable users with the role of the CSR and the Administrator to sort products (tariff plans, additional services) by parameters (name, area of distribution). |
| 17  | The system should provide an opportunity to search for products, orders, and accounts.  |

All business requirements have been met. Also the project is integrated with Gravatar, Google Maps, have dashboards, bulk operations, notifications for CSR  and reports in pdf. 

During the development of the project was used the Scrum methodology 
