sap.ui.define([
    "rcAdminApp/controller/BaseController",
	"sap/m/MessageToast",
	"sap/m/MessageBox",
	"sap/ui/model/Sorter",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
	"sap/ui/model/FilterType",
	"sap/ui/model/json/JSONModel"
],
    /**
     * @param {typeof sap.ui.core.mvc.Controller} Controller
     */
    function (BaseController, MessageToast, MessageBox, Sorter, Filter, FilterOperator, FilterType,
		JSONModel) {
        "use strict";

        return BaseController.extend("rcAdminApp.controller.BerufsfelderView", {
            onInit: function () {
				var oMessageManager = sap.ui.getCore().getMessageManager(),
				oMessageModel = oMessageManager.getMessageModel(),
				oMessageModelBinding = oMessageModel.bindList("/", undefined, [],
					new Filter("technical", FilterOperator.EQ, true)),
				oViewModel = new JSONModel({
					busy : false,
					hasUIChanges : true,
					usernameEmpty : true,
					order : 0
				});

			this.getView().setModel(oViewModel, "appView");
			this.getView().setModel(oMessageModel, "message");

			//oMessageModelBinding.attachChange(this.onMessageBindingChange, this);
			this._bTechnicalErrors = false;
            },

            onAbc: function (oEvent) {
                console.log("Hallo");
                var oItem, oCtx;
			    //oItem = oEvent.getSource();
                oItem = oEvent.getParameter("listItem");
			    oCtx = oItem.getBindingContext();
			    this.getRouter().navTo("veranstaltung",{
				id : oCtx.getProperty("Id")
			});
            },
            onCreate : function () {

            	var oList = this.byId("berufsfelderList"),
            	oBinding = oList.getBinding("items"),

            	oContext = oBinding.create({
            		"Id" : "Neu",
            		"Name" : "Neues Berufsfeld"
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
            	var oSelected = this.byId("berufsfelderList").getSelectedItem();
            	if (oSelected) {
            		oSelected.getBindingContext().delete("$auto").then(function () {
            			MessageToast.show(this._getText("deletionSuccessMessage"));
            		}.bind(this), function (oError) {
            			MessageBox.error(oError.message);
            		});
            	}
            },

		/**
		 * Refresh the data.
		 */
		 onRefresh : function () {
			var oBinding = this.byId("berufsfelderList").getBinding("items");

			if (oBinding.hasPendingChanges()) {
				MessageBox.error(this._getText("refreshNotPossibleMessage"));
				return;
			}
			oBinding.refresh();
			MessageToast.show(this._getText("refreshSuccessMessage"));
		},

		/**
		 * Reset any unsaved changes.
		 */
		onResetChanges : function () {
			this.byId("berufsfelderList").getBinding("items").resetChanges();
			// If there were technical errors, cancelling changes resets them.
			this._bTechnicalErrors = false;
			this._setUIChanges(false);
		},

		/**
		 * Save changes to the source.
		 */
		onSave : function () {
			var fnSuccess = function () {
					this._setBusy(false);
					MessageToast.show(this._getText("changesSentMessage"));
					this._setUIChanges(false);
				}.bind(this),
				fnError = function (oError) {
					this._setBusy(false);
					this._setUIChanges(false);
					MessageBox.error(oError.message);
				}.bind(this);

			this._setBusy(true); // Lock UI until submitBatch is resolved.
			this.getView().getModel().submitBatch("peopleGroup").then(fnSuccess, fnError);
			// If there were technical errors, a new save resets them.
			this._bTechnicalErrors = false;
		},

				/**
		 * Convenience method for retrieving a translatable text.
		 * @param {string} sTextId - the ID of the text to be retrieved.
		 * @param {Array} [aArgs] - optional array of texts for placeholders.
		 * @returns {string} the text belonging to the given ID.
		 */
		_getText : function (sTextId, aArgs) {
			return this.getOwnerComponent().getModel("i18n").getResourceBundle().getText(sTextId,
				aArgs);
		},

		/**
		 * Set hasUIChanges flag in View Model
		 * @param {boolean} [bHasUIChanges] - set or clear hasUIChanges
		 * if bHasUIChanges is not set, the hasPendingChanges-function of the OdataV4 model
		 * determines the result
		 */
		_setUIChanges : function (bHasUIChanges) {
			if (this._bTechnicalErrors) {
				// If there is currently a technical error, then force 'true'.
				bHasUIChanges = true;
			} else if (bHasUIChanges === undefined) {
				bHasUIChanges = this.getView().getModel().hasPendingChanges();
			}
			var oModel = this.getView().getModel("appView");
			oModel.setProperty("/hasUIChanges", bHasUIChanges);
		},

		/**
		 * Set busy flag in View Model
		 * @param {boolean} bIsBusy - set or clear busy
		 */
		_setBusy : function (bIsBusy) {
			var oModel = this.getView().getModel("appView");

			oModel.setProperty("/busy", bIsBusy);
		}
        });
    });
