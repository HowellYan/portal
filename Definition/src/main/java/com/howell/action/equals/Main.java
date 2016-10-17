package com.howell.action.equals;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EqualsJson equalsJson = new EqualsJson();

		String standardData = "{'uid':123,'phone':1,'has_password':true,'location':{'province':true,'city':null},'cpma':'none', 'name':'susie', 'abc':'ss'}";

		String resData = "{'data':{'uid':124,'phone':'1','has_password':False,'location':{'province':true,'city':null},'cpma':null}}";
		equalsJson.respValueAssertion(standardData, resData);
	}

}
