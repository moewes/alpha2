sap.ui.define([
    "rcAdminApp/controller/BaseController"

],
    /**
     * @param {typeof sap.ui.core.mvc.Controller} Controller
     */
    function (BaseController) {
        "use strict";

        return BaseController.extend("rcAdminApp.controller.ReferentenView", {
            onInit: function () {

            },
            onPress: function () {
                sap.m.URLHelper.redirect("https://clouduiapp.azurewebsites.net/api/todos",false);
            },
            onPress2: function () {
                this.getRouter().navTo("html", {}, false /*no history*/);
            },
            onCreate : function () {

            	var oList = this.byId("referentenList"),
            	oBinding = oList.getBinding("items"),

            	oContext = oBinding.create({
            		"Id": "Neu",
            		"Name": "NN",
                    "Email": ""
            	});

            	this._setUIChanges(true);  
            	//this.getView().getModel("appView").setProperty("/usernameEmpty", true); // FIXME

            	// Select and focus the table row that contains the newly created entry
            	oList.getItems().some(function (oItem) {
            	    if (oItem.getBindingContext() === oContext) {
            		    oItem.focus();
            		    oItem.setSelected(true);
            		    return true;
            	    }
            	});
            },
            onDelete : function () {
            	var oSelected = this.byId("referentenList").getSelectedItem();
            	if (oSelected) {
            		oSelected.getBindingContext().delete("$auto").then(function () {
            			MessageToast.show(this._getText("deletionSuccessMessage"));
            		}.bind(this), function (oError) {
            			MessageBox.error(oError.message);
            		});
            	}
            },
            onMail : function () {
              var oSelected = this.byId("referentenList").getSelectedItem();
              if (oSelected) {
                var oModel = this.getView().getModel();
                var oAction = oModel.bindContext("Quarkus.OData.sendMail(...)", oSelected.getBindingContext());

                oAction.execute().then(function () {
                					var oResults = oAction.getBoundContext().getObject();
                					console.log(oResults);
                					MessageToast.show(oResults.value);
                				}).catch( function (error) {

                					MessageToast.show("Es ist ein Fehler aufgetreten. Bitte versuchen Sie es später noch einmal.");
                					console.log("Fehler" + error);
                				});
              }
            }
        });
    });
