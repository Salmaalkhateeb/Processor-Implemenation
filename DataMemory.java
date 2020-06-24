import java.util.Hashtable;

public class DataMemory {
	
		
		//int pc;
		Hashtable<String , String> memory ;
		int Maxsize;
		int currentSize;
		String inputAddress;
		String WriteData;
		boolean MemWrite;         //TODO: to be edited according to our control unit 
		boolean MemRead;		  //TODO: to be edited according to our control unit 
		String ReadData;
		Cache cache;				//TODO: reference it From MainClass
		
		
		

		
		
		public DataMemory() {
		
			this.MemWrite = false;
			this.MemRead = false;
			this.memory = new Hashtable<String, String>();
			this.Maxsize = 1024;
			this.currentSize = 0;
			System.out.println("Data memory created successfully of size " + this.Maxsize);
		}
			
			public void loadIntoDataMemory(String address ,int k) {
										
				String key = address;
				if(Integer.parseInt(address,2)>1024) {
					System.out.println("Unreachable address in memory , memory size is limited to 1024");
					return;
				}
					
				if(currentSize < this.Maxsize) {
					
					this.memory.put(key, Integer.toBinaryString(k));
					System.out.println("Data added to the memory hash with value of " + Integer.toBinaryString(k) + " in address " + key);
					this.currentSize++;
					
					
					
				}
				else {
					System.out.println("Data Memory full , cant input more Data");
				}
				
				
			}
			
			public void retrieveDataFromAddress(String address) {
				String data = "";
				data = this.memory.get(address);
				System.out.println("retrieved data of value " + data );
				this.ReadData = data;
				
				 
			}
			
			public void writeDataIntoAddress(String address , String data) {
				this.memory.put(address, data);
				this.WriteData = data;
				this.inputAddress = address;
				System.out.println("added into data memory data of value " + data + " into address" + address);
			}
			
		
			
		
}
