package main.java.com.defrainphoto.weddingtracker.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

public class Timeline implements Serializable{

	int timelineId;
	List<TimeChunk> timeChunks;
	Time startTime;
	Time totalTime;
	
	public int getTimelineId() {
		return timelineId;
	}
	
	public void setTimelineId(int timelineId) {
		this.timelineId = timelineId;
	}
	
	public List<TimeChunk> getTimeChunks() {
		return timeChunks;
	}
	
	public void setTimeChunks(List<TimeChunk> timeChunks) {
		this.timeChunks = timeChunks;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public Time getTotalTime() {
		return totalTime;
	}
	
	public void setTotalTime(Time totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public String toString() {
		return "Timeline [timelineId=" + timelineId + ", startTime=" + startTime + ", totalTime=" + totalTime + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + timelineId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timeline other = (Timeline) obj;
		if (timelineId != other.timelineId)
			return false;
		return true;
	}
}
