(function() {
	var module = angular.module("couponSystem");

	module.controller("CustomerCtrl", CustomersCtrlCtor)

	function CustomersCtrlCtor(mainAdminServiceHTTP, $ngConfirm) {
		// ===============================================================================================
		this.customers = [];
		this.customer;
		var self = this;
		this.show = false;
		this.showCustomer = false;
		this.order = "";
		this.goUp = false;

		// ===============================================================================================
		// Get All Customers
		// ===============================================================================================
		var promise = mainAdminServiceHTTP.getAllCustomers();
		promise
				.then(
						function(resp) {
							self.customers = resp.data;
						},
						function(err) {
							if (err.status == 401)
								window.location
										.replace("http://localhost:8080/WEB/HTMLs/index.html#/main");

						});

		// ===============================================================================================
		// Delete Customer
		// ===============================================================================================
		this.deleteCustomer = function(id) {

			$ngConfirm({
				title : 'Delete Customer?',
				content : 'This dialog will automatically trigger \'cancel\'  if you don\'t respond.',
				autoClose : 'cancel|8000',
				buttons : {
					deleteUser : {
						text : 'delete Customer',
						btnClass : 'btn-red',
						action : function(button) {
							mainAdminServiceHTTP.deleteCustomer(id)
							$ngConfirm('Deleted...');
							location.reload();
						}
					},
					cancel : function() {
					}
				}
			});
		}
		// ===============================================================================================
		// Get Customer
		// ===============================================================================================
		/**
		 * Done with angular filter...
		 */
		// ===============================================================================================
		// Show update Customer form
		// ===============================================================================================
		this.showUpdateCustomer = function(customer) {
			if (customer.edit) {
				this.hideUpdateCustomer(customer);
			} else {
				$('#successAlert').hide();
				customer.edit = !customer.edit;
			}
			this.show = true;
		}
		// ===============================================================================================
		// Hide update Customer form and Update Customer
		// ===============================================================================================
		this.hideUpdateCustomer = function(customer) {
			var promisePost = mainAdminServiceHTTP.updateCustomer(customer);
			promisePost
					.then(
							function(resp) {
								$('#successAlert').show();
								$('#successAlert').text(
										"Customer with id:" + customer.id
												+ " updated successfuly");

							},
							function(err) {
								if (err.status == 401)
									window.location
											.replace("http://localhost:8080/WEB/HTMLs/index.html#/main");
								else
									$('#errorAlert').show();
								$('#errorAlert').text(err.data);
							});
			this.show = false;
			customer.edit = !customer.edit;
		}
		// ===============================================================================================
		// Order By
		// ===============================================================================================

		this.orderBy = function(header) {
			if (header == self.order) {
				self.goUp = !self.goUp;
			}
			self.order = header;
		}

	}
})();