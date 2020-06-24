
public class Cache {
	
	Block [] cache ;
	int hits;
	int misses;
	DataMemory DMemoryAccess;      //TODO: reference it From MainClass
	
	
	public Cache() {
		
		
		this.cache = new Block[128];	
		this.hits = 0;
		this.misses = 0;
		for(int i = 0 ; i<128 ; i++) {
			cache[i] = new Block();
			
		}
		
	
		
	}

		
	
	public String retrieveFromCache(String address1) {
		//boolean lastelement = false;
		
		//getting index
		int address = Integer.parseInt(address1,2);
		
		int index = address%128;
		
		
		
		
		
		//--------------------
		
		//getting the tag
		int tag = address/128;
		
		//searching
		
		if(this.cache[index].validBit==1) {
			if(this.cache[index].tag==tag) {
				hits++;
				System.out.println("a hit ");
				return this.cache[index].data;
			}
			misses++;
			System.out.println("a miss , tag incorrect , accessing DataMemory");
			this.cache[index].tag = tag;
			//TODO: Go fetch it from ur datamemory
			this.DMemoryAccess.retrieveDataFromAddress(Integer.toBinaryString(address));
			this.cache[index].data=DMemoryAccess.ReadData;
			System.out.println("Added to cache and retrieved from memory data of value : " + DMemoryAccess.ReadData );
			return DMemoryAccess.ReadData;
		}
		
			misses++;
			System.out.println("a miss , valid bit is 0");
			this.cache[index].tag = tag;
			this.cache[index].validBit = 1;
			//TODO: Go fetch it from ur datamemory
			this.DMemoryAccess.retrieveDataFromAddress(Integer.toBinaryString(address));
			this.cache[index].data=DMemoryAccess.ReadData;
			System.out.println("Added to cache and retrieved from memory data of value : " + DMemoryAccess.ReadData );
			return DMemoryAccess.ReadData;
		
		
		
		
		}
		
		

		
		
		
	
		
		
		
		
		
		
	}


