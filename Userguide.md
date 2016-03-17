Author: Priya Packrisamy

# Unmarshalling ATOM response #

Resting now comes with builtin deserializer for ATOM response format as well. This deserializer has been rewritten to work even if the value object skips some of the XML elements and attributes. In other words, the deserializer checks if the value object defines a specific variable before invoking the set method. This check avoids the construction failed exceptions and need to declare variables even if they are not used.

This quick tutorial will walk you through the various supported formats and explain how the XML alias should be setup in each case. **Please note that this capability is available in resting 0.7 release onwards.**


#### Scenario 1: The ATOM response contains elements only from default namespace namely "http://www.w3.org/2005/Atom" ####

This is the simplest and straightforward scenario. Resting defines Java classes that can be readily used. Refer com.google.resting.atom package for these classes. Here is an example of how to unmarshal response into com.google.resting.atom.AtomFeed object.


```

List<AtomFeed> l = Resting.restByATOM(
                                      "http://books.google.com/books/feeds/volumes?q=php", 
                                       80, 
                                       null,
                                       Verb.GET, 
                                       AtomFeed.class,
                                       new XMLAlias(),
                                       EncodingTypes.UTF8 , 
                                       null
                                      );

```

As shown in the example above, XML alias can be passed without explicitly specifying any alias objects. Because it is internally customized to include default namespace elements.

#### Scenario 2: The ATOM response contains elements from other namespaces as well. These could be custom defined or opensearch related namespaces ####

First define a value object that extends AtomFeed as shown below and include only the custom attributes that need to be unmarshalled.

```
public class SampleFeed extends AtomFeed {

	private String queryEvaluationTime;
	private String totalResults;
	
	public String getQueryEvaluationTime() {
		return queryEvaluationTime;
	}
	public void setQueryEvaluationTime(String queryEvaluationTime) {
		this.queryEvaluationTime = queryEvaluationTime;
	}
	public String getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}
}
```

Now invoke Resting as shown below.
```

List<SampleFeed> l = Resting.restByATOM(
                                        "http://books.google.com/books/feeds/volumes?q=php", 
                                        80,
                                        null,
                                        Verb.GET, 
                                        SampleFeed.class,
                                        new XMLAlias(),
                                        EncodingTypes.UTF8 , 
                                        null
                                       );

```

Suppose you want to include some external object references in the SampleFeed class, you need to setup the XML alias accordingly while invoking Resting. See examples below.

```
public class SampleFeedWithObjectRef extends SampleFeed {

	private OpenSearchQuery query;

	public OpenSearchQuery getQuery() {
		return query;
	}

	public void setQuery(OpenSearchQuery query) {
		this.query = query;
	}
}

XMLAlias xmlAlias = new XMLAlias();
xmlAlias.add("query", OpenSearchQuery.class);
List<SampleFeedWithObjectRef> l = Resting.restByATOM(
                                                     "http://books.google.com/books/feeds/volumes?q=php", 
                                                     80, 
                                                     null, 
                                                     Verb.GET, 
                                                     SampleFeedWithObjectRef.class,
                                                     new XMLAlias(),
                                                     EncodingTypes.UTF8 , 
                                                     null
                                                    );


```

#### Scenario 3: The ATOM response is in collection format ####

Define XML alias object with proper mappings for API response and collection configuration as shown below.

```

XMLAlias xmlAlias = new XMLAlias();
xmlAlias.add("apiResponse", APIResponse.class);
xmlAlias.add("collectionConfig", OFCollection.class);
List<APIResponse> l = Resting.restByATOM(
                                         "http://books.google.com/books/feeds/volumes?q=php", 
                                         80, 
                                         null,
                                         Verb.GET, 
                                         APIResponse.class,
                                         new XMLAlias(),
                                         EncodingTypes.UTF8 , 
                                         null
                                        );

```
#### Testcases: ####

Resting source includes sample ATOM files and JUnit testcases for the above mentioned scenarios. Refer TransformerTest.java and RestingTest.java classes in <i>test.com.google.resting</i> package for details. The Junit testcase reads all the XML files from test/resources/atom folder. So make sure to set this folder in classpath before running the test case. You can also import the Eclipse .classpath file available with the source code.