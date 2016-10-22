package org.usfirst.frc.team3130.misc;

public class Toggle <T> {
	
	//Define variable used for handling edge detection and toggling
	private T m_status;
	private T m_toggleVal1;
	private T m_toggleVal2;
	private boolean m_risingEdgeValid;
	private boolean m_fallingEdgeValid;
	
	public enum edgeType{
		kRisingEdge,
		kDualEdge,
		kFallingEdge
	}
	
	//toggle1Val is returned first, with toggle2Val returning after a toggle
	//with them swapping on sucessive toggles
	public Toggle(T toggle1Val, T toggle2Val)
	{
		m_status = toggle1Val;
		m_toggleVal1 = toggle1Val;
		m_toggleVal2 = toggle2Val;
		
		m_risingEdgeValid = true;
		m_fallingEdgeValid = true;
	}
	
	public boolean risingEdge(boolean varToEdgeCheck)
	{
		if(varToEdgeCheck){
			if(m_risingEdgeValid){
				m_risingEdgeValid = false;
				return true;
			}
		}else{
			m_risingEdgeValid = true;
			return false;
		}
		return false;
	}
	
	public boolean fallingEdge(boolean varToEdgeCheck)
	{
		if(!varToEdgeCheck){
			if(m_fallingEdgeValid){
				m_fallingEdgeValid = false;
				return true;
			}
		}else{
			m_fallingEdgeValid = true;
			return false;
		}
		return false;
	}
	
	public void toggleStatus()
	{
		if(m_status == m_toggleVal1){
			m_status = m_toggleVal2;
		}else{
			m_status = m_toggleVal1;
		}
	}
	
	public boolean dualEdge(boolean varToEdgeCheck)
	{
		return fallingEdge(varToEdgeCheck) || risingEdge(varToEdgeCheck);
	}
	
	//edgeMode is used to change which type of edge is detected. If one isn't provided it will use kRisingEdge
	//The options are kRisingEdge, kDualEdge, and kFallingEdge
	public T toggleStatusOnEdgeChange(boolean varToEdgeCheck, edgeType edgeMode)
	{
		if(edgeMode == edgeType.kFallingEdge && fallingEdge(varToEdgeCheck)){
			toggleStatus();
		}else if(edgeMode == edgeType.kDualEdge && dualEdge(varToEdgeCheck)){
			toggleStatus();
		}else if(edgeMode == edgeType.kRisingEdge && risingEdge(varToEdgeCheck)){
			toggleStatus();
		}
		return getStatus();
	}
	
	public T getStatus(){
		return m_status;
	}
	
	//Sets the current state of the Toggle. 
	//If not one of the values set in the constructor, the method will silently fail.
	public void setStatus(T valueToSet){
		if(valueToSet == m_toggleVal1 || valueToSet == m_toggleVal2) m_status = valueToSet;
	}
}
