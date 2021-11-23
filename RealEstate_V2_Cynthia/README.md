![image](https://cdn.discordapp.com/attachments/787363155494830091/907941779733618708/unknown.png)

# Readme de REALESTATE
### (BackEnd) 


> Esta api Rest con Spring tiene la intención de modelar el funcionamiento de una inmobiliaria con diferentes entidades y
> asociaciones entre ellas. Partiendo de 'Vivienda' como base e interrelacionandolo con 'Inmobiliarias',
> que tienen la posibilidad de estar asociadas a una vivienda, 'propietarios' que son los que tienen estas 'Viviendas'
> e 'Interesados' que son aquellas personas que pueden interesarse por una 'Vivienda'.


![image](https://cdn.discordapp.com/attachments/787363155494830091/907941667447930890/unknown.png)

## Instalacion

Para poder arrancar la Api Rest es necesario disponer de una aplicación que permita ejecutar proyectos maven, un ejemplo
podría ser un IDE como Intellij u otro cualquiera.
Hacer un git clone del repositorio que está alojado en el usuario miguelcamposedu.
Todo el proecto está basado en Springboot y en java17
El servidor se abre en el puerto 8080 por lo que hay que buscarlo en el navegador :
```sh
127.0.0.1:8000
```

## Pomp

Las dependencias que usaremos para eeste proyecto serán

- [H2DataBase] - Un gestor de base de datos que nos proporcionará una persistencia de datos en un xml
- [SpringWeb] - Será nuestro servidor web local
- [Lombok] - Una herramienta muy útil que proporcionará atajos a la hora de construir entidades y relaciones.
- [OpneApi3] - Será con Swagger la manera de comentar los controladores de manera cómoda con anotaciones
- [SpringJpa] - Será la menera que tengamos de de acceder a la base de datos basada en Hibernate

## Controladores

| Controller | Descripcción |
| ------ | ------ |
| InmobiliariaController | En este controllador meteremos todos los endPoints que tengan que ver con inmobiliarias |
| InteresaController | En este controllador meteremos todos los endPoints que tengan que ver con Interesa |
| PropietarioController | En este controllador meteremos todos los endPoints que tengan que ver con inmobiliarias |
| ViviendaController |En este controllador meteremos todos los endPoints que tengan que ver con inmobiliarias |


#### InmobiliariaController
```sh
getListInmobiliaria  //  Devuelve la lista paginada de inmobiliarias
addInmo  //  Crea una nueva inmobiliaria 
removeInmo  //  Borra una inmobiliaria
allInmoShort  //  Devuelve una lista de inmobiliarias SIN paginar y con un contenido reducido
```

#### ViviendaController
```sh
getAllViviendas  //  Devuelve una lista paginada de viviendas
getDetallesViviendaPropietario  //  Devuelve un Dto de Vivienda con datos del propietario y inmobiliaria
addVivienda  //  Crea una vivienda y un propietario si lo necesita, si no asocia la vivienda a un propietario
addInteresadoVivienda  //  Crea la asociación de un interesado con una vivienda
addNuevoInteresadoAVivienda  //  Crea un interesado y lo asocia a una vivienda
deleteMeInteresa  //  Borra el interés de un interesado por una vivienda
deleteViviendaAndIntereses  //  Borra una vivienda y los intereses de esta vivienda
asociarViviendaInmobiliaria  //  Edita la asociación de una vivienda con una inmobiliaria
desasociarViviendaInmobiliaria  //  Elimina la asociacion de una vivienda y una inmobiliaria
putVivienda  // Edita los datos de una vivienda
totalInteresados  //  Contabiliaza los los interesados de una vivienda
filtroViviendas  //  Devuelve una lista filtrada por diferentes parámetros de las viviendas
topViviendas  //  Devuelve una lista de las 10 primeras viviendas por las que más se han interesado
```

#### PropietarioController
```sh
getAllProp  //  Devuelve la lista de propietarios paginada
getDetailPropietario  // Devuelve los detalles de un propietario
allInteresado  // Devuelve una lista SIN paginar de interesados abreviada
deltePropietario  // Borra un propietario y sus viviendas
```

#### InteresadoController

```sh
getAllInteresados  //  Devuelve una lista paginada de todos los interesados
allInteresados  //  Devuelve la lista SIN paginar de los interesados
```

 Creado por: Alejandro Bajo, Manuel Fernandez, Cynthia Labrador y Pablo Repiso