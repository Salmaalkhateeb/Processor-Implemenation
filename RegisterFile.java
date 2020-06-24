

public class RegisterFile {
	
	
	Register [] registers;
	Register readRegister1;
	Register readRegister2;
	Register writeRegister;
	Register zeroRegister;
	boolean writeflag;                //TODO: to be adjusted according to our control unit flags
	public  String readData1;
	public  String readData2;
	public  String writeData;
	
	public RegisterFile() {
		this.registers = new Register[16];
	
		for(int i = 0 ; i<registers.length ; i++) {
			registers[i] = new Register();
			this.assignRegisterID(i);
			
		}
		this.zeroRegister = registers[15];
		this.zeroRegister.theRegister = "0";
		this.writeflag = false;
		System.out.println("RegisterFile is created successfully of size 16 ");
	}
	
	public void assignRegisterID(int i) {
		
		
		String IdUnextended = Integer.toBinaryString(i);
		while(IdUnextended.length()<4) {
			IdUnextended = "0" + IdUnextended;
		}
		this.registers[i].RegID = IdUnextended;
		
		
	}
	
	public void clear() {
		this.readRegister1 = null;
		this.readRegister2 = null;
		this.writeRegister = null;
	}
	
	public static void main(String[] args) {
		
	
		
	}
	
	
	
	

}
