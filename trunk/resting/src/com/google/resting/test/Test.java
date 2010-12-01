package com.google.resting.test;

import java.util.List;

import com.google.resting.Resting;
import com.google.resting.component.OperationType;
import com.google.resting.component.RequestParams;
import com.google.resting.component.impl.Alias;
import com.google.resting.component.impl.BasicRequestParams;
import com.google.resting.component.impl.ServiceResponse;
import com.google.resting.helper.RestingHelper;
import com.google.resting.transform.impl.XMLTransformer;
/**
 * Temporary test class.
 * 
 * @author sujata.de
 *
 */
public class Test {
	public static void main(String[] args) {
/*		RequestParams params = new BasicRequestParams();
		params.add("appid", "YD-9G7bey8_JXxQP6rxl.fBFGgCdNjoDMACQA--");
		params.add("state", "CA");
		ServiceResponse serviceResponse=Resting.get("http://local.yahooapis.com/MapsService/V1/geocode?appid=YD-9G7bey8_JXxQP6rxl.fBFGgCdNjoDMACQA--&state=CA");
		System.out.println("[Resting]The raw HTTP response from Yahoo Map REST API is\n--------- \n"+serviceResponse.getResponseString());
*/				

		XMLTransformer<ResultSet> transformer=new XMLTransformer<ResultSet>();
		Alias alias=new Alias().add("Result", Result.class).add("ResultSet", ResultSet.class);
/*
		List<ResultSet> results=transformer.getEntityList(serviceResponse, ResultSet.class,alias);
		System.out.println("\n-----------\n[Resting]The resultant object is \n ------\n"+results.toString());
*/		
		//List<ResultSet> resultset=Resting.getByXML("http://local.yahooapis.com/MapsService/V1/geocode?appid=YD-9G7bey8_JXxQP6rxl.fBFGgCdNjoDMACQA--&state=CA", ResultSet.class, alias);
		//System.out.println(resultset.toString());
		RequestParams params = new BasicRequestParams();
		params.add("facets", "[\"brandNameFacet\"]");
		params.add("key", "<your key>");
		
		//ServiceResponse serviceResponse=RestingHelper.execute("http://api.zappos.com/Search",params,OperationType.GET);
		List<Facets> facets=Resting.getByJSON("http://api.zappos.com/Search",params, Facets.class, "facets");
		System.out.println(facets.get(0).getValues().size());
	}

}//Test 