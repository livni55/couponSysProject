(function() {
	var module = angular.module("couponSystem");

	module.controller("CompaniesCtrl", CompaniesCtrlCtor)

	function CompaniesCtrlCtor(mainAdminServiceHTTP, $ngConfirm) {
		// ===============================================================================================
		this.companies = [];
		this.company;
		var self = this;
		this.show = false;
		this.showCompany = false;
		this.order = "";
		this.goUp = false;
		// ===============================================================================================
		// Create Company:
		// ===============================================================================================

		// ===============================================================================================
		// Get All companies
		// ===============================================================================================
		var promise = mainAdminServiceHTTP.getAllCompanies();
		promise
				.then(
						function(resp) {
							self.companies = resp.data;
						},
						function(err) {
							if (err.status == 401)
								window.location
										.replace("http://localhost:8080/WEB/HTMLs/index.html#/main");

						});

		// ===============================================================================================
		// Delete Company
		// ===============================================================================================
		this.deleteCompany = function(id) {

			$ngConfirm({
				title : 'Delete Company?',
				content : 'This dialog will automatically trigger \'cancel\'  if you don\'t respond.',
				autoClose : 'cancel|8000',
				buttons : {
					deleteUser : {
						text : 'delete Company',
						btnClass : 'btn-red',
						action : function(button) {
							mainAdminServiceHTTP.deleteCompany(id)
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
		// Get Company
		// ===============================================================================================
		/**
		 * Done with angular filter...
		 */
		// ===============================================================================================
		// Show update company form
		// ===============================================================================================
		this.showUpdateCompany = function(company) {
			if (company.edit) {
				this.hideUpdateCompany(company);
			} else {
				$('#successAlert').hide();
				company.edit = !company.edit;
			}
			this.show = true;
		}
		// ===============================================================================================
		// Hide update company form and Update Company
		// ===============================================================================================
		this.hideUpdateCompany = function(company) {
			var promisePost = mainAdminServiceHTTP.updateCompany(company);
			promisePost
					.then(
							function(resp) {
								$('#successAlert').show();
								$('#successAlert').text(
										"Company with id:" + company.id
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
			company.edit = !company.edit;
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