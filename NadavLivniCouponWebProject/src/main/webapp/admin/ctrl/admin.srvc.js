(function () {

    var module = angular.module("couponSystem");

    module.service("mainAdminServiceHTTP", mainAdminServiceHTTPCtor);

    function mainAdminServiceHTTPCtor( $http) {


        //*********************************   c o m p a n y   f u n c t i o n ************************************ */

        //create company /**************************************************** */
        this.createCompany = function (company) {
			return $http.post('http://localhost:8080/CouponWebService/webapi/adminservice/createcompany', company);
        }

        //delete company //***************************************************** */
        this.deleteCompany = function (idComp) {
        	return $http.delete('http://localhost:8080/CouponWebService/webapi/adminservice/deletecompany/' + idComp);
        }

        //update company //***************************************************** */
        this.updateCompany = function (updateCompany) {
        	return $http.put('http://localhost:8080/CouponWebService/webapi/adminservice/updatecompany/'+updateCompany.id, updateCompany);
       }

        //get company By Id //*************************************************** */
        this.getCompanyById = function (idCom) {
        	return $http.get('http://localhost:8080/CouponWebService/webapi/adminservice/getcompany/' + idCom);
        }
        
        //get All Companies //****************************************************** */
        this.getAllCompanies = function () {
        	return $http.get('http://localhost:8080/CouponWebService/webapi/adminservice/getallcompanies/');
        }
        
        //*********************************c u s t o m e r    f u n c t i o n ************************************ */

        //create customer /**************************************************** */
        this.createCustomer = function (customer) {
        	return $http.post('http://localhost:8080/CouponWebService/webapi/adminservice/createcustomer' ,customer );
        }

        //delete Customer //***************************************************** */
        this.deleteCustomer = function (idCust) {
        	return $http.delete('http://localhost:8080/CouponWebService/webapi/adminservice/deletecustomer/' + idCust  );
        }

        //update customer //***************************************************** */
        this.updateCustomer = function (updateCustomer) {
        	return $http.put('http://localhost:8080/CouponWebService/webapi/adminservice/updatecustomer/'+updateCustomer.id, updateCustomer  );
        }
        //get Customer By Id //*************************************************** */
        this.getCustomerById = function (idCust) {
        	return $http.get('http://localhost:8080/CouponWebService/webapi/adminservice/getcustomer/' + idCust  );
        }

        //get All Customers //****************************************************** */
        this.getAllCustomers = function () {
        	return $http.get('http://localhost:8080/CouponWebService/webapi/adminservice/getallcustomers');
        }
    }

})();