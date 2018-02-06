package pk_Crawler;

public class Notice {

	private String id;
	private String title;
	private String link;
	private int state;
	private long creatTime;
	private String from;
	
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(long creatTime) {
		this.creatTime = creatTime;
	}
	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", link=" + link + ", state=" + state + ", creatTime="
				+ creatTime + "]";
	}
	
	
}
