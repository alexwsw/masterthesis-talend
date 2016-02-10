package metadata;

import java.util.List;

import column.IColumn;

public class Metadata implements IMetadata {
	
	private String label, name;
	private EMetadataTypes connector;
	private List<IColumn> columns;

}
