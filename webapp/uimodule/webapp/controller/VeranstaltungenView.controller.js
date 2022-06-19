sap.ui.define([
    "rcAdminApp/controller/BaseController"

],
    /**
     * @param {typeof sap.ui.core.mvc.Controller} Controller
     */
    function (BaseController) {
        "use strict";

        return BaseController.extend("rcAdminApp.controller.VeranstaltungenView", {
            onInit: function () {
                console.log("init");
                var oRouter = this.getRouter();
                oRouter.getRoute("veranstaltungen").attachMatched(this._onRouteMatched, this);
            },
            _onRouteMatched : function (oEvent) {
                console.log("route matched");
                var oList = this.byId("veranstaltungenList"),
            	oBinding = oList.getBinding("items");
                oBinding.refresh();
            },
            onRefresh : function () {
                var oList = this.byId("veranstaltungenList"),
            	oBinding = oList.getBinding("items");
                oBinding.refresh();
            },
            onDetail: function (oEvent) {
              
                var oItem, oCtx;
                oItem = oEvent.getParameter("listItem");
			    oCtx = oItem.getBindingContext();
			    this.getRouter().navTo("veranstaltung",{
				id : oCtx.getProperty("Id")
			});
            },
            onCreate : function () {

            	var oList = this.byId("veranstaltungenList"),
            	oBinding = oList.getBinding("items");
                var oRouter = this.getRouter();

                oBinding.attachCreateCompleted(function(oEvent) {
                    let newContext = oEvent.getParameter("context");
                    console.log(newContext.getProperty("Id"));

                    oRouter.navTo("veranstaltung", {id : newContext.getProperty("Id")})

                }.bind(this));
                
            	var oContext = oBinding.create({
            		"Id": "Neu",
            		
            	});
            },
            onDelete : function () {
            	var oSelected = this.byId("veranstaltungenList").getSelectedItem();
            	if (oSelected) {
            		oSelected.getBindingContext().delete("$auto").then(function () {
            			MessageToast.show(this._getText("deletionSuccessMessage"));
            		}.bind(this), function (oError) {
            			MessageBox.error(oError.message);
            		});
            	}
            }
        });
    });
