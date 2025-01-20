package UD02A02_XML_DOM;

public class Item {

	private String Name, seq, type, status, desc;

	public Item(String name, String seq, String type, String status, String desc) {
		super();
		Name = name;
		this.seq = seq;
		this.type = type;
		this.status = status;
		this.desc = desc;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
