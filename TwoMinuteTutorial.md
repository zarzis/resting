# Two Minute Tutorial #

This is a very quick tutorial to demonstrate how easy it is to use resting.

Suppose  your REST URI is a weather service by Yahoo: http://local.yahooapis.com/MapsService/V1/geocode?appid=YD-9G7bey8_JXxQP6rxl.fBFGgCdNjoDMACQA--&state=CA. You wish to execute a GET request to get the weather details of your location.


#### Option 1 (Simplest) : Pass the entire URI and read the HTTP response as a String ####
```
ServiceResponse response=Resting.get("http://local.yahooapis.com/MapsService/V1/geocode?appid=YD-9G7bey8_JXxQP6rxl.fBFGgCdNjoDMACQA--&state=CA",80);

System.out.println(response); //Print the response.
```
#### Option 2 : Pass a `RequestParams` object along withe base URI (Recommended for JSON/complex encoding issue) ####

`RequestParams` is the collection of data in(key=value) format.
Pass it along with the base URI (http://.././ path).

```
RequestParams params = new BasicRequestParams(); 

params.add("appid", "YD-9G7bey8_JXxQP6rxl.fBFGgCdNjoDMACQA--");

params.add("state", "CA");

ServiceResponse response=Resting.get("http://local.yahooapis.com/MapsService/V1/geocode",80,params); 

System.out.println(response); //Print the response.
```
### Create your custom JAVA objects from HTTP response. ###

##### A. Transform JSON response: #####
Suppose your JSON response is of the format given below.
```
{
 "statusCode":"200"
 ,"product":
 [
  {
   "productId":"1234567","productName":"pen"  
  }
 ,
  {
   "productId":"7564933","productName":"pencil"
  }
 ]
}
```
You wish to retrieve the list of "product" objects. Here, "product" is the JSON alias:

```
RequestParams jsonParams = new JSONRequestParams(); //Create request params 

jsonParams.add("key", "fdb3c385a8d22d174cafeadc6d4c1405b08d5609");

//Get the list of Product objects by passing "product" as alias
List<Product> products=Resting.getByJSON("http://api.zappos.com/Product/7564933",80,jsonParams, Product.class, "product");
```
where Product is a client side value object:
```
public class Product {
 private int productId;
 private String productName;       
}
```
##### B. Transform XML response: #####
Suppose your XML response is of the format:
```
<?xml version="1.0" ?> 
- <ResultSet xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:yahoo:maps" xsi:schemaLocation="urn:yahoo:maps http://local.yahooapis.com/MapsService/V1/GeocodeResponse.xsd">
 - <Result precision="state">
    <Latitude>37.271881</Latitude> 
    <Longitude>-119.270233</Longitude> 
    <Address /> 
    <City /> 
    <State>CA</State> 
    <Zip /> 
    <Country>US</Country> 
   </Result>
  </ResultSet>
```
The resting code will be similar to the following:
```
//Create an alias object which will help transforming each embedded object from XML to Java.
XMLAlias alias=new XMLAlias().add("Result", Result.class).add("ResultSet", ResultSet.class); 

ResultSet resultset=Resting.getByXML("http://local.yahooapis.com/MapsService/V1/geocode", 80,params,ResultSet.class, alias);
```
where `Result` and `ResultSet` are client side value objects, preferably auto generated from the <a href='http://local.yahooapis.com/MapsService/V1/GeocodeResponse.xsd'>XSD</a>. If you choose to handcode, the classes can be like this:
```
public class ResultSet {
 private Result Result;
}
```
and
```
public class Result {
 private String precision;
 private String Latitude;
 private String Longitude;
 private String Address;
 private String City;
 private String State;
 private String Zip;
 private String Country;
}
```

## Recommended Approach: Use `RestingBuilder` for All Transformations: ##

`RestingBuilder` gives you a lot of flexibility and yields minimalistic clean code.

```
RestingBuilder<Product> builder=new RestingBuilder<Product>("http://api.zappos.com/Product/7564933",Product.class)
									.setRequestParams(jsonParams)
									.setProxy("proxy.abcd.ac.in", 3128,"user","password");

List<Product> products=builder.build();
```
Using `RestingBuilder`, coding is minimal. `RestingBuilder` assumes a lot of default parameters, such as,
```
Request type = GET
Port=80
Encoding type=UTF 8
Response format = JSON
```
Only when non-default parameters are used, they will have to be explicitly set in the builder.


