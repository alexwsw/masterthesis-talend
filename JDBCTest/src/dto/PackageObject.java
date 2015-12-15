package dto;

import java.util.List;

public class PackageObject extends AbstractObject implements IPackageObject {
			
		
		private String ID;	
		private String name;		
		private String DQ1;
		private List<IColumnObject> dq1def;
		private String DQ2;	
		private List<IColumnObject> dq2def;
		private String DQ3;	
		private List<IColumnObject> dq3def;
		private String DBCommand;		
		private String destinationTable;
		private List<IColumnObject> destinationTableDef;
		private List<String> PKColumn;		
		private List<String> PKNameColumn;		
		private String TLInsertPKN;		
		private String TLUpdatePKN;		
		private String OnErrorHandling;
		
		public PackageObject (PackageDTO p) {
			this.ID = p.getID();
			this.name = p.getName();
			this.DQ1 = p.getDQ1();
			this.DQ2 = p.getDQ2();
			this.DQ3 = p.getDQ3();
			this.DBCommand = p.getDBCommand();
			this.destinationTable = p.getDestinationTable();
			this.PKColumn = splitPackageColumnsAndEvaluate(p.getPKColumn());
			this.PKNameColumn = splitPackageColumnsAndEvaluate(p.getPKNameColumn());
			this.TLInsertPKN = p.getTLInsertPKN();
			this.TLUpdatePKN = p.getTLUpdatePKN();
			this.OnErrorHandling = p.getOnErrorHandling();
		}
		
		
		public String getID() {
			return ID;
		}
		public void setID(String id) {
			this.ID = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDQ1() {
			return DQ1;
		}
		public void setDQ1(String DQ1) {
			this.DQ1 = DQ1;
		}
		public String getDQ2() {
			return DQ2;
		}
		public void setDQ2(String DQ2) {
			this.DQ2 = DQ2;
		}
		public String getDQ3() {
			return DQ3;
		}
		public void setDQ3(String DQ3) {
			this.DQ3 = DQ3;
		}
		public String getDBCommand() {
			return DBCommand;
		}
		public void setDBCommand(String dbCommand) {
			this.DBCommand = dbCommand;
		}
		public String getDestinationTable() {
			return destinationTable;
		}
		public void setDestinationTable(String destinationTable) {
			this.destinationTable = destinationTable;
		}
		public List<String> getPKColumn() {
			return PKColumn;
		}
		public void setPKColumn(List<String> pKColumn) {
			PKColumn = pKColumn;
		}
		public List<String> getPKNameColumn() {
			return PKNameColumn;
		}
		public void setPKNameColumn(List<String> pKNameColumn) {
			PKNameColumn = pKNameColumn;
		}
		public String getTLInsertPKN() {
			return TLInsertPKN;
		}
		public void setTLInsertPKN(String tLInsertPKN) {
			TLInsertPKN = tLInsertPKN;
		}
		public String getTLUpdatePKN() {
			return TLUpdatePKN;
		}
		public void setTLUpdatePKN(String tLUpdatePKN) {
			TLUpdatePKN = tLUpdatePKN;
		}
		public String getOnErrorHandling() {
			return OnErrorHandling;
		}
		public void setOnErrorHandling(String isOnErrorHandling) {
			this.OnErrorHandling = isOnErrorHandling;
		}
		public List<IColumnObject> getDq1def() {
			return dq1def;
		}
		public void setDq1def(List<IColumnObject> dq1def) {
			this.dq1def = dq1def;
		}
		public List<IColumnObject> getDq2def() {
			return dq2def;
		}
		public void setDq2def(List<IColumnObject> dq2def) {
			this.dq2def = dq2def;
		}
		public List<IColumnObject> getDq3def() {
			return dq3def;
		}
		public void setDq3def(List<IColumnObject> dq3def) {
			this.dq3def = dq3def;
		}
		public List<IColumnObject> getDestinationTableDef() {
			return destinationTableDef;
		}
		public void setDestinationTableDef(List<IColumnObject> destinationTableDef) {
			this.destinationTableDef = destinationTableDef;
		}


		@Override
		public String toString() {
			return "PackageObject [ID=" + ID + ", name=" + name + ", DQ1="
					+ DQ1 + ", dq1def=" + dq1def + ", DQ2=" + DQ2 + ", dq2def="
					+ dq2def + ", DQ3=" + DQ3 + ", dq3def=" + dq3def
					+ ", DBCommand=" + DBCommand + ", destinationTable="
					+ destinationTable + ", destinationTableDef="
					+ destinationTableDef + ", PKColumn=" + PKColumn
					+ ", PKNameColumn=" + PKNameColumn + ", TLInsertPKN="
					+ TLInsertPKN + ", TLUpdatePKN=" + TLUpdatePKN
					+ ", OnErrorHandling=" + OnErrorHandling + "]";
		}


		
		
		
		
		
	

}
