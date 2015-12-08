package elements;

public enum EDataAction {
	
	INSERT("INSERT"),
	UPDATE("UPDATE");
	
	private final String dataAction;
	
		private EDataAction(String dataAction) {
			this.dataAction = dataAction;
		}		
		public String getType() {
			return dataAction.toString();
		}


	}

