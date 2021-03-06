(function() {

	var module = angular.module("couponSystem");

	module.service("mainCompanyService", mainCompanyServiceCtor);
	
	
	
	function mainCompanyServiceCtor($q, $http) {
		
		// create coupon ()
		this.createCoupon = function(coupon) {
			return $http.post('http://localhost:8080/CouponWebService/webapi/companyservice/createcoupon', coupon);
		}
			
		// delete coupon //************************* */
		this.deleteCoupon = function(idCoup) {
			return $http.delete('http://localhost:8080/CouponWebService/webapi/companyservice/deletecoupon/'+idCoup);
		}

		// update coupon //************************ */
		this.updateCoupon = function(updatedCoupon) {
			return $http.put('http://localhost:8080/CouponWebService/webapi/companyservice/updatecoupon/'+ updatedCoupon.id, updatedCoupon);
		}

		// get All Coupons //************************* */
		this.getAllCoupons = function() {
			return $http.get('http://localhost:8080/CouponWebService/webapi/companyservice/getallcoupons');
		}
		
		// get Coupon By Id //************************* */
		this.getCouponById = function(idNum) {
			return $http.get('http://localhost:8080/CouponWebService/webapi/companyservice/getcoupon/'+idNum);
		}

		// get Coupons By Type //************************* */
		this.getCouponsByType = function(type) {
			return $http.get('http://localhost:8080/CouponWebService/webapi/companyservice/getcouponsbytype/'+type);
		}

		// get Coupons By Price //************************* */
		this.getCouponsByPrice = function(price) {
			return $http.get('http://localhost:8080/CouponWebService/webapi/companyservice/getcouponsbymaxprice/'+price);
		}

		// //get Coupons By Date //************************* */
		this.getCouponsByDate = function(myDate) {
			return $http.get('http://localhost:8080/CouponWebService/webapi/companyservice/getcouponsbyenddate/'+myDate);
		}
		
		// get company ()
		this.getCompany = function () {
			return $http.get('http://localhost:8080/CouponWebService/webapi/companyservice/currentcompany/');
			
		}

	}

})();