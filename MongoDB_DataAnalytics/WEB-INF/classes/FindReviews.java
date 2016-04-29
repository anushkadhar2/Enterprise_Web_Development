import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.AggregationOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class FindReviews extends HttpServlet {
	
	int val = 0;
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		PrintWriter output = response.getWriter();
					
		DB db = mongo.getDB("Tutorial_3");
		
		// If the collection does not exists, MongoDB will create it for you
		DBCollection myReviews = db.getCollection("myReviews");
		
		BasicDBObject query = new BasicDBObject();
				
		try {
			
			// Get the form data
			String productName = request.getParameter("productName");
			int productPrice = Integer.parseInt(request.getParameter("productPrice"));
			String retailerZip = request.getParameter("retailerZip");
			String retailerCity = request.getParameter("retailerCity");
			String productCategory = request.getParameter("productCategory");
			int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
			String compareRating = request.getParameter("compareRating");
			String comparePrice = request.getParameter("comparePrice");
			String retailerName = request.getParameter("retailerName");
			String retailerState = request.getParameter("retailerState");
			String productOnSale = request.getParameter("productOnSale");
			String manufacturerName = request.getParameter("manufacturerName");
			String manufacturerRebate = request.getParameter("manufacturerRebate");
			String userName = request.getParameter("userName");
			String userOccupation = request.getParameter("userOccupation");
			String reviewDate = request.getParameter("reviewDate");
			String reviewText = request.getParameter("reviewText");
			String textSearch = request.getParameter("textSearch");
			//String comparePrice1 = request.getParameter("comparePrice1");
			String returnValueDropdown = request.getParameter("returnValue");
			
			String groupByDropdown = request.getParameter("groupByDropdown");
			String trendByDropdown = request.getParameter("trendByDropdown");
			String advanceByDropdown = request.getParameter("advanceByDropdown");
			
			//Boolean flags to check the filter settings
			boolean noFilter = false;
			boolean counter = false;
			boolean counter2 = false;
			boolean counter3 = false;
			boolean searchByPattern = false;
			boolean filterByProduct = false;
			boolean filterByPrice = false;
			boolean filterByZip = false;
			boolean filterByCity = false;
			boolean filterByRating = false;
			boolean filterByProductCategory = false;
			
			
			//HIGHEST PRICE
			boolean filterByHighestPrice = false;
			
			boolean filterByRetailerName = false;
			boolean filterByRetailerState = false;
			boolean filterByUserName = false;
			boolean filterByReviewDate = false;
			boolean filterByUserAge = false;
			boolean filterByUserGender = false;
			boolean filterByUserOccupation = false;
			boolean filterByProductOnSale = false;
			boolean filterByManufacturerName = false;
			boolean filterByManufacturerRebate = false;
			boolean filterByReviewText = false;
			boolean filterByRetailerZipWithHighestPrice = false;
			boolean filterByRetailerCityWithHighestPrice = false;
			
			boolean groupBy = false;
			boolean groupByCity = false;
			boolean groupByProduct = false;
			boolean groupByPrice = false;
			boolean groupByProductSale = false;
			boolean groupByRating = false;
			boolean groupByDate = false;
			boolean groupByZip = false;
			
			boolean groupByRetailerName = false;
			boolean groupByRetailerState = false;
			boolean groupByRetailerZipWithHighestPrice = false;
			boolean groupByRetailerCityWithHighestPrice = false;
			
			boolean groupByCityWithRatingFive = false;
			boolean groupByCityWithTopProducts = false;
			
			boolean groupByUserName = false;
		    boolean groupByUserAge = false;
		    boolean groupByUserGender = false;
			boolean groupByUserOccupation = false;
			boolean groupByManufacturerName = false;
			boolean groupByManufacturerRebate = false;
			
			boolean trendBy = false;
			boolean trendByCity = false;
			boolean trendByProduct = false;
			
			boolean advanceBy = false;
			boolean advanceByCity = false;
			boolean advanceByAgeAndCity = false;
			
			boolean countOnly = false;
						
			//Get the filters selected
			//Filter - Simple Search
			String[] filters = request.getParameterValues("queryCheckBox");
			//Filters - Group By
			String[] extraSettings = request.getParameterValues("extraSettings");
			//Filters - Trend By
			String[] trendSettings = request.getParameterValues("trendSettings");
			//Filters - Advance By
			String[] advanceSettings = request.getParameterValues("advanceSettings");
			//Filters - Text Search
			String[] searchFilter = request.getParameterValues("searchRadio");
			
			DBCursor dbCursor = null;
			AggregationOutput aggregateData = null;
			
			//Check for extra settings(Grouping Settings)
			if(extraSettings != null){				
				//User has selected extra settings
				groupBy = true;
				
				for(int x = 0; x <extraSettings.length; x++){
					switch (extraSettings[x]){						
						case "COUNT_ONLY":
							//Not implemented functionality to return count only
							countOnly = true;				
							break;
						case "GROUP_BY":	
							//Can add more grouping conditions here
							if(groupByDropdown.equals("GROUP_BY_CITY")){
								groupByCity = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCT")){
								groupByProduct = true;
							} else if(groupByDropdown.equals("GROUP_BY_PRODUCT_PRICE")){
								groupByPrice = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCT_SALE")){
								groupByProductSale = true;
							}  else if(groupByDropdown.equals("GROUP_BY_RETAILER_NAME"))	{
								groupByRetailerName = true;
							}else if(groupByDropdown.equals("GROUP_BY_RETAILER_STATE"))	{
								groupByRetailerState = true;
							}else if(groupByDropdown.equals("GROUP_BY_USER_NAME"))	{
								groupByUserName = true;
							}else if(groupByDropdown.equals("GROUP_BY_USER_AGE"))	{
								groupByUserAge = true;
							}else if(groupByDropdown.equals("GROUP_BY_USER_GENDER"))	{
								groupByUserGender = true;
							}else if(groupByDropdown.equals("GROUP_BY_USER_OCCUPATION"))	{
								groupByUserOccupation = true;
							
							}
							//else if(groupByDropdown.equals("GROUP_BY_HIGHEST_PRICE_ZIP_CODE")){
								//groupByRetailerZipWithHighestPrice = true;
							//}
							//else if(groupByDropdown.equals("GROUP_BY_HIGHEST_PRICE_CITY")){
							//	groupByRetailerCityWithHighestPrice = true;
							//}
							//else if(groupByDropdown.equals("GROUP_BY_PRODUCTS_REVIEWED_WITH_RATING_CITY")){
							//	groupByCityWithRatingFive = true;
							//}
							else if(groupByDropdown.equals("GROUP_BY_TOPLIKEDPRODUCTS_CITY")){
								groupByCityWithTopProducts = true;
							}else if(groupByDropdown.equals("GROUP_BY_MANUFACTURER_NAME")){
								groupByManufacturerName = true;
							}else if(groupByDropdown.equals("GROUP_BY_MANUFACTURER_REBATE")){
								groupByManufacturerRebate = true;
							}else if(groupByDropdown.equals("GROUP_BY_RATING")){
								groupByRating = true;
							}else if(groupByDropdown.equals("GROUP_BY_DATE")){
								groupByDate = true;
							}else if(groupByDropdown.equals("GROUP_BY_ZIP")){
								groupByZip = true;
							}							
							break;
					}		
				}				
			}			
			
			if(trendSettings != null){				
				//User has selected extra settings
				trendBy = true;
				
				for(int x = 0; x <trendSettings.length; x++){
					switch (trendSettings[x]){						
						case "COUNT_ONLY":
							//Not implemented functionality to return count only
							countOnly = true;				
							break;
						case "TREND_BY":	
							//Can add more grouping conditions here
							if(trendByDropdown.equals("TREND_BY_CITY")){
								trendByCity = true;
							}else if(trendByDropdown.equals("TREND_BY_PRODUCT")){
								trendByProduct = true;
							} 
							break;
					}		
				}				
			}
			if(advanceSettings != null){				
				//User has selected advance settings
				advanceBy = true;
				
				for(int x = 0; x <advanceSettings.length; x++){
					switch (advanceSettings[x]){						
						case "COUNT_ONLY":
							//Not implemented functionality to return count only
							countOnly = true;				
							break;
						case "ADVANCE_BY":	
							//Can add more grouping conditions here
							if(advanceByDropdown.equals("ADVANCE_BY_CITY")){
								advanceByCity = true;
							}else if(advanceByDropdown.equals("ADVANCE_BY_AGE_CITY")){
								advanceByAgeAndCity = true;
							} 
							break;
					}		
				}				
			}
			
			if(searchFilter != null ){
				
				for (int i = 0; i < searchFilter.length; i++){
					
					switch (searchFilter[i]){
						
						case "textSearch":
							searchByPattern = true;
							query.put("reviewText",java.util.regex.Pattern.compile(textSearch));
							myReviews.find(query);
							break;
						
						default:
							noFilter = true;
							break;
					}
				}
				
				
			}
			
			//Check the main filters only if the 'groupBy' option is not selected
			if(filters != null ){
				for (int i = 0; i < filters.length; i++) {
					//Check what all filters are ON
					//Build the query accordingly
					switch (filters[i]){										
						case "productName":							
							filterByProduct = true;
							if(!productName.equals("ALL_PRODUCTS")){
								query.put("productName", productName);
							}						
							break;
												
						case "productPrice":
							filterByPrice = true;
							if (comparePrice.equals("EQUALS_TO")) {
								query.put("productPrice", productPrice);
							}else if(comparePrice.equals("GREATER_THAN")){
								query.put("productPrice", new BasicDBObject("$gt", productPrice));
							}else if(comparePrice.equals("LESS_THAN")){
								query.put("productPrice", new BasicDBObject("$lt", productPrice));
							}else if(comparePrice.equals("HIGHEST")){
								counter = true;
							}
							break;
												
						case "retailerZip":
							filterByZip = true;
							query.put("retailerZip", retailerZip);
							break;
						
						case "userOccupation":
							filterByUserOccupation = true;
							query.put("userOccupation",userOccupation);
							break;
						case "reviewDate":
							filterByReviewDate = true;
							query.put("reviewDate",reviewDate);
							break;
							
						case "productCategory":
							filterByProductCategory = true;
							if(!productCategory.equals("All")){
							query.put("productCategory", productCategory);
							}
							break;
						case "retailerName": 
							filterByRetailerName = true;
							if(!retailerName.equals("All") && !groupByCity){
								query.put("retailerName", retailerName);
							}							
							break;
							
						case "userName":
							filterByUserName = true;
							query.put("userName", userName);
							break;
						
						case "reviewText":
							filterByReviewText = true;
							query.put("reviewText", reviewText);
							break;
							
						case "manufacturerRebate":
							filterByManufacturerRebate = true;
							query.put("manufacturerRebate", manufacturerRebate);
							break;
						
						case "manufacturerName":
							filterByZip = true;
							query.put("manufacturerName", manufacturerName);
							break;
						case "productOnSale":
							filterByProductOnSale = true;
							query.put("productOnSale", productOnSale);
							break;
												
						case "retailerCity": 
							filterByCity = true;
							if(!retailerCity.equals("All") && !groupByCity){
								query.put("retailerCity", retailerCity);
							}							
							break;
						case "retailerState": 
							filterByRetailerState = true;
							if(!retailerState.equals("All") && !groupByCity){
								query.put("retailerState", retailerState);
							}							
							break;
												
						case "reviewRating":	
							filterByRating = true;
							if (compareRating.equals("EQUALS_TO")) {
								
								query.put("reviewRating",reviewRating);
								counter2 = true;
							}else{
								query.put("reviewRating", new BasicDBObject("$gt", reviewRating));
							}
							break;
						
						
						default:
							//Show all the reviews if nothing is selected
							noFilter = true;
							break;						
					}				
				}
			}
			
			else{
				//Show all the reviews if nothing is selected
				noFilter = true;
			}
			if (returnValueDropdown.equals("TOP_5")){
					//Top 5 - Sorted by review rating
					counter3 = true;
					val = 5;
				}else if (returnValueDropdown.equals("TOP_10")){
					//Top 10 - Sorted by review rating
					counter3 = true;
					val = 10;
				}			
					
						
			//Construct the top of the page
			constructPageTop(output);
						
			//Run the query 
			if(groupBy == true){		
				//Run the query using aggregate function
				DBObject match = null;
				DBObject groupFields = null;
				DBObject group = null;
				DBObject projectFields = null;
				DBObject project = null;
				DBObject sort = null;
				AggregationOutput aggregate = null;
				
				if(groupByCity){
					if(counter){
						
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("highestPrice", new BasicDBObject("$max","$productPrice"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("productPrice",-1));
					//limit = new BasicDBObject("$limit", 1);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("HighestPrice","$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort, group, project);
					
								
					//Construct the page content
					constructGroupByCityWithHighestPriceContent(aggregate, output, countOnly);
					}
					else if(counter3){
						
						groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					//groupFields.put("highestPrice", new BasicDBObject("$max","$productPrice"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("reviewRating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("reviewRating",-1));
					//limit = new BasicDBObject("$limit", 1);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					//projectFields.put("HighestPrice","$highestPrice");
					projectFields.put("ReviewRating", "$reviewRating");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort, group, project);
					
								
					//Construct the page content
					constructGroupByCityWithTopProductsContent(aggregate, output, countOnly);
					} 
					else if(counter2){
						match = new BasicDBObject("$match", new BasicDBObject("reviewRating",5));
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(match,group, project);
												
					//Construct the page content
					constructGroupByCityWithRatingFiveContent(aggregate, output, countOnly);
						
					}
					else{
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("price", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$price");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByCityContent(aggregate, output, countOnly);
					
				}
				}
				
				if(groupByRating){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$reviewRating");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("ReviewRating", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByRatingContent(aggregate, output, countOnly);
					
				}if(groupByDate){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$reviewDate");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("ReviewDate", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByDateContent(aggregate, output, countOnly);
					
				}if(groupByManufacturerName){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$manufacturerName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("ManufacturerName", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByManufacturerNameContent(aggregate, output, countOnly);
					
				}if(groupByManufacturerRebate){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$manufacturerRebate");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("ManufacturerRebate", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByManufacturerRebateContent(aggregate, output, countOnly);
					
				}
				if(groupByZip){
					
					if (counter){
						
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerZip");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("highestPrice", new BasicDBObject("$max","$productPrice"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("productPrice",-1));
					//limit = new BasicDBObject("$limit", 1);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Zip", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("HighestPrice","$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort, group, project);
					
								
					//Construct the page content
					constructGroupByZipWithHighestPriceContent(aggregate, output, countOnly);
					}else{
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerZip");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("price", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("RetailerZip", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$price");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByZipContent(aggregate, output, countOnly);
					
				}
				}
				
				/*if(groupByCityWithRatingFive){
					
					match = new BasicDBObject("$match", new BasicDBObject("reviewRating",5));
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(match,group, project);
												
					//Construct the page content
					constructGroupByCityWithRatingFiveContent(aggregate, output, countOnly);
					
				}
				*/
				/*if(groupByRetailerZipWithHighestPrice){
					
					
						
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerZip");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("highestPrice", new BasicDBObject("$max","$productPrice"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("productPrice",-1));
					//limit = new BasicDBObject("$limit", 1);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Zip", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("HighestPrice","$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort, group, project);
					
								
					//Construct the page content
					constructGroupByZipWithHighestPriceContent(aggregate, output, countOnly);
					
				}
				*/if(groupByCityWithTopProducts){
					
					
						
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					//groupFields.put("highestPrice", new BasicDBObject("$max","$productPrice"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("reviewRating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("reviewRating",-1));
					//limit = new BasicDBObject("$limit", 1);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					//projectFields.put("HighestPrice","$highestPrice");
					projectFields.put("ReviewRating", "$reviewRating");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort, group, project);
					
								
					//Construct the page content
					constructGroupByCityWithTopProductsContent(aggregate, output, countOnly);
					
				}
				/*if(groupByRetailerCityWithHighestPrice){
					
					
						
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("highestPrice", new BasicDBObject("$max","$productPrice"));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("productPrice",-1));
					//limit = new BasicDBObject("$limit", 1);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("HighestPrice","$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort, group, project);
					
								
					//Construct the page content
					constructGroupByCityWithHighestPriceContent(aggregate, output, countOnly);
					
				}*/
				else if(groupByRetailerName){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Retailer", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByRetailerNameContent(aggregate, output, countOnly);
						
				}else if(groupByRetailerState){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerState");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("RetailerState", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByRetailerStateContent(aggregate, output, countOnly);
						
				}else if(groupByUserName){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$userName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("UserName", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByUserNameContent(aggregate, output, countOnly);
						
				}else if(groupByUserAge){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$userAge");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("UserAge", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByUserAgeContent(aggregate, output, countOnly);
						
				}else if(groupByUserGender){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$userGender");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("UserGender", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByUserGenderContent(aggregate, output, countOnly);
						
				}else if(groupByUserOccupation){
					
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$userOccupation");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("UserOccupation", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByUserOccupationContent(aggregate, output, countOnly);
						
				}	else if(groupByPrice){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$productPrice");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("ProductPrice", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);				
							
					//Construct the page content
					constructGroupByProductPriceContent(aggregate, output, countOnly);
					
				}else if(groupByProductSale){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$productOnSale");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("ProductOnSale", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);				
							
					//Construct the page content
					constructGroupByProductOnSaleContent(aggregate, output, countOnly);
					
				}
				else if(groupByProduct){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$productName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Product", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);				
							
					//Construct the page content
					constructGroupByProductContent(aggregate, output, countOnly);
					
				}			 
			}
			else if(trendBy ==true){
				DBObject match = null;
				DBObject groupFields = null;
				DBObject group = null;
				DBObject projectFields = null;
				DBObject project = null;
				DBObject sort = null;
				DBObject limit = null;
				AggregationOutput aggregate = null;
				
				if(trendByCity){ 
				    
				    match = new BasicDBObject("$match", new BasicDBObject("reviewRating",5));
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", new BasicDBObject("Retailer City","$retailerCity").append("Product Name", "$productName"));
					groupFields.put("count", new BasicDBObject("$sum", 1));
					//groupFields.put("highestPrice", new BasicDBObject("$max","$productPrice"));
					
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					//groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					//groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("count",-1));
					//limit = new BasicDBObject("$limit", 1);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					//projectFields.put("HighestPrice","$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					//projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(match,group, project);
												
					//Construct the page content
					constructTrendByCityContent(aggregate, output, countOnly);
					
				}
			}
			else if(advanceBy ==true){
				DBObject match = null;
				DBObject groupFields = null;
				DBObject group = null;
				DBObject projectFields = null;
				DBObject project = null;
				DBObject sort = null;
				DBObject limit = null;
				AggregationOutput aggregate = null;
				
				if(advanceByCity){ 
				    
				    //match = new BasicDBObject("$match", new BasicDBObject("reviewRating",5));
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", new BasicDBObject("Retailer City","$retailerCity").append("Retailer Name", "$retailerName"));
					groupFields.put("count", new BasicDBObject("$sum", 1));
					//groupFields.put("highestPrice", new BasicDBObject("$max","$productPrice"));
					
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					//groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					//groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("productPrice",-1).append("reviewRating",-1));
					//limit = new BasicDBObject("$limit", 1);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					//projectFields.put("HighestPrice","$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					//projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort,group, project);
												
					//Construct the page content
					constructAdvanceByCityContent(aggregate, output, countOnly);
					
				}
				if(advanceByAgeAndCity){ 
				    
				    //match = new BasicDBObject("$match", new BasicDBObject("reviewRating",5));
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					//groupFields.put("highestPrice", new BasicDBObject("$max","$productPrice"));
					
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					//groupFields.put("productPrice", new BasicDBObject("$push", "$productPrice"));
					//groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					groupFields.put("userAge", new BasicDBObject("$push", "$userAge"));
					
					group = new BasicDBObject("$group", groupFields);
					sort = new BasicDBObject("$sort", new BasicDBObject("userAge",-1));
					//limit = new BasicDBObject("$limit", 1);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");
					projectFields.put("Review Count", "$count");
					//projectFields.put("HighestPrice","$highestPrice");
					projectFields.put("Product", "$productName");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					projectFields.put("Age", "$userAge");
					//projectFields.put("ProductPrice", "$productPrice");
					
					project = new BasicDBObject("$project", projectFields);
					
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(sort,group, project);
												
					//Construct the page content
					constructAdvanceByAgeAndCityContent(aggregate, output, countOnly);
					
				}
			}
			else{
				//Check the return value selected
				int returnLimit = 0;
				
				//Create sort variable
				DBObject sort = new BasicDBObject();
												
				 if (returnValueDropdown.equals("LATEST_5")){
					//Latest 5 - Sort by date
					returnLimit = 5;
					sort.put("reviewDate",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("LATEST_10")){
					//Latest 10 - Sort by date
					returnLimit = 10;
					sort.put("reviewDate",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else{
					//Run the simple search query(default result)
					dbCursor = myReviews.find(query);
				}		
				
				//Construct the page content
				constructDefaultContent(dbCursor, output, countOnly);
			}			
			
			//Construct the bottom of the page
			constructPageBottom(output);
			
			
		} 
		catch (MongoException e) {
			e.printStackTrace();
		}

	}
	
	public void constructPageTop(PrintWriter output){
		String pageHeading = "Query Result";
		String myPageTop = "<!DOCTYPE html>" + "<html lang=\"en\">"
					+ "<head>	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
					+ "<title>Game Speed</title>"
					+ "<link rel=\"stylesheet\" href=\"datastyles.css\" type=\"text/css\" />"
					+ "</head>"
					+ "<body>"
					+ "<div id=\"container\">"
					+ "<header>"
					+ "<h1><a href=\"/\">GameSpeed<span></span></a></h1><h2>Tutorial 3</h2>"
					+ "</header>"
					+ "<nav>"
					+ "<ul>"
					+ "<li class=\"\"><a href=\"index.html\">Home</a></li>"
					+ "<li class = \"\"><a href=\"Microsoft\">Write Review</a></li>"
					+ "<li class = \"start selected\"><a href=\"DataAnalytics.html\">Data Analytics</a></li>"
					+ "</ul>"
					+ "</nav>"
					+ "<div id=\"body\">"
					+ "<section id=\"review-content\">"
					+ "<article>"
					+ "<h2 style=\"color:#DE2D3A;font-weight:700;\">" +pageHeading + "</h2>";
		
		output.println(myPageTop);		
	}
	
	public void constructPageBottom(PrintWriter output){
		String myPageBottom = "</article>"
					+ "</section>"
                    + "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<footer>"
					+ "<div class=\"footer-content\">"
					+ "<ul>"
                    + "<li>"
                    + "<h4>Dummy Link Section 1</h4>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 1</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 2</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link  3</a>"
                    + "</li>"
					+ "</ul>"
					+ "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>CSP 595 - Enterprise Web Application - Assignment#3</p>"
					+ "</div>"
					+ "</footer>"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		
		output.println(myPageBottom);
	}
	
	public void constructDefaultContent(DBCursor dbCursor, PrintWriter output, boolean countOnly){
		int count = 0;
		String tableData = " ";
		String pageContent = " ";

		while (dbCursor.hasNext()) {		
			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
			tableData =  "<tr><td>Name: <b>     " + bobj.getString("productName") + " </b></td></tr>"
			
						+ "<tr><td>Price:       " + bobj.getInt("productPrice") + "</br>"
						+ "Product Category:            " + bobj.getString("productCategory") + "</br>"
						+ "Retailer Name:            " + bobj.getString("retailerName") + "</br>"
						+ "Retailer Zipcode:    " + bobj.getString("retailerZip") + "</br>"
						+ "Retailer City:       " + bobj.getString("retailerCity") + "</br>"
						+ "Retailer State:      " + bobj.getString("retailerState") + "</br>"
						+ "Product On Sale:                " + bobj.getString("productOnSale") + "</br>"
						+ "User Name:             " + bobj.getString("userName") + "</br>"
						+ "User Age:            " + bobj.getString("userAge") + "</br>"
						+ "User Gender:         " + bobj.getString("userGender") + "</br>"
						+ "User Occupation:     " + bobj.getString("userOccupation") + "</br>"
						//+ "Manufacturer:        " + bobj.getString("manufacturerName") + "</br>"
						+ "Manufacturer Rebate: " + bobj.getString("manufacturerRebate") + "</br>"
						+ "Rating:              " + bobj.getString("reviewRating") + "</br>"
						+ "Date:                " + bobj.getString("reviewDate") + "</br>"
						+ "Review Text:         " + bobj.getString("reviewText") + "</td></tr>";
				
			count++;
				
				output.println("<h3>"+count+"</h3>");
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
		}

		//No data found
		if(count == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
	public void constructGroupByCityContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList priceList = (BasicDBList) bobj.get("Price");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Price: "+priceList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}	
		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByZipContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Zip </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList priceList = (BasicDBList) bobj.get("Price");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>RetailerZip: "+bobj.getString("RetailerZip")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Price: "+priceList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}	
		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
	}

	public void constructGroupByDateContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Date </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Date: "+bobj.getString("ReviewDate")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}	
		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByRatingContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Rating </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>ReviewRating: "+bobj.getString("ReviewRating")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}	
		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByManufacturerRebateContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - ManufacturerRebate </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>ManufacturerRebate: "+bobj.getString("ManufacturerRebate")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}	
		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
		public void constructGroupByManufacturerNameContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - ManufacturerName </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>ManufacturerName: "+bobj.getString("ManufacturerName")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}	
		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
	//Trend function
	
	public void constructTrendByCityContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Trending By City </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				//BasicDBList productPrice = (BasicDBList) bobj.get("ProductPrice");
				//int High = Integer.parseInt(bobj.getString("HighestPrice"));
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				//while (productCount < productList.size()) {
					
					//if(productPrice.get(productCount) == High){
						tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							//+   "ProductPrice: "+productPrice.get(0)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					//}
					
					
					//productCount++;					
				//}	
				
				//BasicDBObject query  = new BasicDBObject();
				//query.put("productName", new BasicDBObject("$max", productPrice));
				//DBCursor dbCursor = myReviews.find(query);
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructAdvanceByCityContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Advance Search By City </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				//BasicDBList productPrice = (BasicDBList) bobj.get("ProductPrice");
				//int High = Integer.parseInt(bobj.getString("HighestPrice"));
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				//while (productCount < productList.size()) {
					
					//if(productPrice.get(productCount) == High){
						if(productList.size()>5){
							for(productCount = 0; productCount<5;++productCount){
								tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							//+   "ProductPrice: "+productPrice.get(0)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
								pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
								output.println(pageContent);
							}
						}else{
							for(productCount = 0; productCount<productList.size();++productCount)
							{
								tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							//+   "ProductPrice: "+productPrice.get(0)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
								pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
								output.println(pageContent);
							}
						}
						
												
					
					//}
					
					
					//productCount++;					
				//}	
				
				//BasicDBObject query  = new BasicDBObject();
				//query.put("productName", new BasicDBObject("$max", productPrice));
				//DBCursor dbCursor = myReviews.find(query);
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructAdvanceByAgeAndCityContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		
		output.println("<h1> Advance Search By Age And City </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				//BasicDBList productPrice = (BasicDBList) bobj.get("ProductPrice");
				//int High = Integer.parseInt(bobj.getString("HighestPrice"));
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				List<Integer> reviewerAgeList = (ArrayList<Integer>)bobj.get("Age");
				
				/*rowCount++;
				
						
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				*/
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					
					
						if(reviewerAgeList.get(productCount)>50){
					
					tableData = "<tr><td>City: "+bobj.getString("City")+"</td>"
							+	"<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "User Age: "+reviewerAgeList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
								pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
								output.println(pageContent);
							
						}
						
												
					
					
					
					
					productCount++;					
				}	
				
				//BasicDBObject query  = new BasicDBObject();
				//query.put("productName", new BasicDBObject("$max", productPrice));
				//DBCursor dbCursor = myReviews.find(query);
				//Reset product count
				productCount =0;
		}
		
	}
	public void constructGroupByCityWithRatingFiveContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City With Rating Five </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByCityWithTopProductsContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City With TOP MOST LIKED PRODUCTS</h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList productPrice = (BasicDBList) bobj.get("ProductPrice");
				BasicDBList rating = (BasicDBList) bobj.get("ReviewRating");
				//int High = Integer.parseInt(bobj.getString("HighestPrice"));
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
				        //+   "<td>Rating: "+bobj.getString("ReviewRating")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				//while (productCount < productList.size()) {
					
					//if(productPrice.get(productCount) == High){
						
						if(productList.size()>5){
							for(productCount = 0; productCount <5; ++productCount){
								tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "ProductPrice: "+productPrice.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
							}
						}else{
							for (productCount = 0; productCount < productList.size(); ++productCount){
								tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "ProductPrice: "+productPrice.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
							}
						}
						
					//}
					
					
					//productCount++;					
				//}	
				
				//BasicDBObject query  = new BasicDBObject();
				//query.put("productName", new BasicDBObject("$max", productPrice));
				//DBCursor dbCursor = myReviews.find(query);
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByZipWithHighestPriceContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Zip With Highest Price  Product</h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList productPrice = (BasicDBList) bobj.get("ProductPrice");
				int High = Integer.parseInt(bobj.getString("HighestPrice"));
				
				rowCount++;
				tableData = "<tr><td>Zip: "+bobj.getString("Zip")+"</td>&nbsp"
				        +   "<td>Highest Price: "+bobj.getString("HighestPrice")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				//while (productCount < productList.size()) {
					
					//if(productPrice.get(productCount) == High){
						tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							+   "ProductPrice: "+productPrice.get(0)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					//}
					
					
					//productCount++;					
				//}	
				
				//BasicDBObject query  = new BasicDBObject();
				//query.put("productName", new BasicDBObject("$max", productPrice));
				//DBCursor dbCursor = myReviews.find(query);
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
	}
	public void constructGroupByCityWithHighestPriceContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City With Highest Price  Product</h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList productPrice = (BasicDBList) bobj.get("ProductPrice");
				int High = Integer.parseInt(bobj.getString("HighestPrice"));
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
				        +   "<td>Highest Price: "+bobj.getString("HighestPrice")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				//while (productCount < productList.size()) {
					
					//if(productPrice.get(productCount) == High){
						tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(0)+"</br>"
							+   "ProductPrice: "+productPrice.get(0)+"</br>"
							+	"Review: "+productReview.get(0)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					//}
					
					
					//productCount++;					
				//}	
				
				//BasicDBObject query  = new BasicDBObject();
				//query.put("productName", new BasicDBObject("$max", productPrice));
				//DBCursor dbCursor = myReviews.find(query);
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
	}	
	public void constructGroupByRetailerNameContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Retailer Name </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>RetailerName: "+bobj.getString("Retailer")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByRetailerStateContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Retailer Name </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>RetailerState: "+bobj.getString("RetailerState")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByUserNameContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Retailer Name </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>UserName: "+bobj.getString("UserName")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByUserAgeContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Retailer Name </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>UserAge: "+bobj.getString("UserAge")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}public void constructGroupByUserGenderContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Retailer Name </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>UserGender: "+bobj.getString("UserGender")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByUserOccupationContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - Retailer Name </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>UserOccupation: "+bobj.getString("UserOccupation")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByProductPriceContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		output.println("<h1> Grouped By - Products </h1>");
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>ProductPrice: "+bobj.getString("ProductPrice")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (reviewCount < productReview.size()) {
					tableData = "<tr rowspan = \"3\"><td>Rating: "+rating.get(reviewCount)+"</br>"
								+ "Review: "+productReview.get(reviewCount)+"</td></tr>";
							
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					reviewCount++;					
				}
					
				//Reset review count
				reviewCount = 0;
					
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByProductOnSaleContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		output.println("<h1> Grouped By - Products </h1>");
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>ProductOnSale: "+bobj.getString("ProductOnSale")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (reviewCount < productReview.size()) {
					tableData = "<tr rowspan = \"3\"><td>Rating: "+rating.get(reviewCount)+"</br>"
								+ "Review: "+productReview.get(reviewCount)+"</td></tr>";
							
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					reviewCount++;					
				}
					
				//Reset review count
				reviewCount = 0;
					
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	public void constructGroupByProductContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		output.println("<h1> Grouped By - Products </h1>");
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Product: "+bobj.getString("Product")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (reviewCount < productReview.size()) {
					tableData = "<tr rowspan = \"3\"><td>Rating: "+rating.get(reviewCount)+"</br>"
								+ "Review: "+productReview.get(reviewCount)+"</td></tr>";
							
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					reviewCount++;					
				}
					
				//Reset review count
				reviewCount = 0;
					
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
}