import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Processor {

	ALU Alu;
	ALUControl AluCont;
	Cache Cache;
	DataMemory DMemory;
	InstructionMemory IMemory;
	MainControl MainCont;
	RegisterFile RegFile;
	ArrayList<Instruction> runningInstructions;
	boolean fetching;
	boolean decoding;
	boolean executing;
	boolean accessingmemory;
	boolean writingback;
	
	public String SignExtend(Instruction I) {
		String bits32 = "";

		bits32 = I.immediate;
		
		char duplicate = bits32.charAt(0);

		while(bits32.length()<16) {
			bits32 = duplicate + bits32;
		}
		return bits32;
	}
	public static void main(String[] args) throws Exception {
		
		Processor p = new Processor();
		//TODO: BEFORE TESTING : PLEASE MAKE SURE THE REGISTERS IN THE CORRESPONDING ADDRESSES HAVE VALUES
		//TODO: please use this to fill registers :        p.RegFile.registers[index].theRegister = "bits";  WHERE INDEX IS THE ADDRESS , BITS IS THE VALUE
		
		//p.IMemory.loadIntoInstructionMemory("1010010000110101"); //Sw from $5 into memory using immediate and $4
		//p.IMemory.loadIntoInstructionMemory("1001010000110101"); //Lw into $5 using memory immediate and $4
		//p.IMemory.loadIntoInstructionMemory("0000100001110001"); add mesh faker eh
		//p.IMemory.loadIntoInstructionMemory("0111010000110101"); add immediate $4 + 3 and write into $5
		//p.IMemory.loadIntoInstructionMemory("1000010000110101"); or immediate $4 or 3 and write into $5
		//p.IMemory.loadIntoInstructionMemory("1011010000110101");  branch not equal $3 and $4 , should branch successfuly
		//p.IMemory.loadIntoInstructionMemory("1100010000110101");  branch op1>op2 , should fail in this case
		//p.IMemory.loadIntoInstructionMemory("0010100001110001");    multiply contents of $7 and $8 then write into &1
		//p.IMemory.loadIntoInstructionMemory("0100100001110001");      //shift left on $8 using value from %7 and write into &1
		//p.IMemory.loadIntoInstructionMemory("0101100001110001");		//shift right on $8 using value from %7 and write into &1
		//p.RegFile.registers[4].theRegister = "1100";
		//p.RegFile.registers[5].theRegister = "0000100111";
		//p.RegFile.registers[3].theRegister = "1111";
		//p.RegFile.registers[8].theRegister = "1111";
		//p.RegFile.registers[7].theRegister = "010";
		
		//TODO: TO TEST INDIVIDUAL INSTRUCTIONS USE THESE 5 LINES
//		Instruction I = p.fetch();
//		p.decode(I);
//		p.execute(I);
//		p.memAccess(I);
//		p.writeBack(I);
		
		
		
		p.RegFile.registers[8].theRegister = "1111";
		p.RegFile.registers[7].theRegister = "010";
		p.RegFile.registers[4].theRegister= "1100";
		p.RegFile.registers[6].theRegister="1000";
		
		p.DMemory.writeDataIntoAddress("1111", "11001001011"); //the data loaded in memory in address 1111
		p.IMemory.loadIntoInstructionMemory("1001011001111111");  //lw into $3 by adding address inside $6 to immediate
		p.IMemory.loadIntoInstructionMemory("0111010001010111"); //addi $4 + immediate 5 and write it into $7
		p.IMemory.loadIntoInstructionMemory("0010100001110001"); //multiply $8 and $7 then write into $1
		
		
		p.pipelineExecution(); //this method allows for pipelining
		
		
		
		
		
	}
	
	public void pipelineExecution() {
		
		for(int i = 1 ; i<50 ; i++) {
			System.out.println("%%%%%%%%%%   Starting clock cycle (" + i + ")  %%%%%%%%%%%%%%");
			if(i<= this.IMemory.currentSize) {
			Instruction I =this.fetch();
			I.tookturn = true;
			}
			
			
			for (int j = 0; j < runningInstructions.size(); j++) {
				Instruction k = runningInstructions.get(j);
				
				if(!k.tookturn && k.fetched &&!k.decoded) {
					//TODO: decode it
					this.decode(k);
					k.tookturn = true;
				}
				if(!k.tookturn && k.fetched && k.decoded && !k.executed) {
					this.execute(k);
					k.tookturn = true;
				}
				if(!k.tookturn && k.fetched && k.decoded && k.executed && !k.accessed) {
					this.memAccess(k);
					k.tookturn = true;
				}
				
				if(!k.tookturn && k.fetched && k.decoded && k.executed && k.accessed && !k.writtenback) {
					this.writeBack(k);
					k.tookturn = true;
					runningInstructions.remove(k);
					j--;
				}
				
				
				
			}
			//Reseting the tookturn to false for a new clock cycle incoming
			for(int y = 0 ; y < runningInstructions.size() ; y++) {
				Instruction g = runningInstructions.get(y);
				g.tookturn = false;
			}
			
		}
		
	}
	
	
	public Processor() {
		Alu = new ALU();
		AluCont = new ALUControl();
		Cache = new Cache();
		DMemory = new DataMemory();
		IMemory = new InstructionMemory();
		MainCont = new MainControl();
		RegFile = new RegisterFile();
		runningInstructions = new ArrayList<Instruction>();
		Cache.DMemoryAccess = DMemory;
		
	}
	
	public Instruction fetch() {
		System.out.println("__________________ Starting fetching ________________");
		Instruction I = null;
		if(!fetching) {
			 I = this.IMemory.getNextInstruction();
			runningInstructions.add(I);
			I.fetched = true;
			I.ID = runningInstructions.size();
			System.out.println("Fetched Instruction of ID " + I.ID);
			System.out.println("__________________ ending fetching ________________");
		}
		return I;
	}
	
	public void decode(Instruction I) {
		System.out.println("__________________ Starting decoding ________________");
		System.out.println(" decoding instruction of ID " + I.ID);
		
		
		Register readReg1 = null;
		Register readReg2 = null;
		Register writeReg = null;
		String readreg1ID = "";
		String readreg2ID = "";
		String writeregID = "";
		String readData1 = "";
		String readData2 = "";
		//TODO: configure the Instruction by calling the methods in instruction class according to the opcode 
		switch(I.op) {
		case("0000"):
		case("0001"):
		case("0010"):
		case("0011"):
		case("0100"):
		case("0101"):
		case("0110"):
		I.fillR1();
		I.fillR2();
		I.fillRd();
		
		
		//TODO: decode into the RegFile
	
		readreg1ID = I.r1;
		readreg2ID = I.r2;
		writeregID = I.rd;	
		
		for(int i = 0 ; i<this.RegFile.registers.length; i++) {
			if(this.RegFile.registers[i].RegID.equals(readreg1ID)) {
				 readReg1 = this.RegFile.registers[i];
			}
			if(this.RegFile.registers[i].RegID.equals(readreg2ID)) {
				 readReg2 = this.RegFile.registers[i];
			}
			if(this.RegFile.registers[i].RegID.equals(writeregID)) {
				 writeReg = this.RegFile.registers[i];
			}
		
		}	
		readData1 = readReg1.theRegister;
		readData2 = readReg2.theRegister;
		RegFile.readData1 = readData1;
		RegFile.readData2 = readData2;
		RegFile.readRegister1 = readReg1;
		RegFile.readRegister2 = readReg2;	
		//ONLY FOR R Type
		RegFile.writeRegister = writeReg;
		System.out.println("Instruction is an R type");

		System.out.println("ReadData1 is : " + RegFile.readData1 +" and in Register of ID " + readReg1.RegID);	
		System.out.println("ReadData2 is : " + RegFile.readData2 +" and in Register of ID " + readReg2.RegID);
		
		
		//TODO: run the Main control evaluator , then assign the booleans into the attributes inside the instruction
		this.MainCont.evaluateSignals(I.op);
		I.WriteBack = MainCont.WriteBack;
		I.UseImmediateForALU = MainCont.UseImmediateForALU;
		I.PCtoBranch = MainCont.PCtoBranch;
		I.MemRead = MainCont.MemRead;
		I.MemWrite = MainCont.MemWrite;
		//I.MemToReg = MainCont.MemToReg;
		
		this.AluCont.opcode = I.op;
		I.ALU4bitoperation = this.AluCont.evaluateALU4Bit();
		I.type = "R";
		//TODO: turn the decoded flag = true
		I.decoded = true;
		System.out.println("WB controls: " + "WriteBack: " +I.WriteBack);
		System.out.println("MEM controls : " + "MemRead: "+I.MemRead + ", MemWrite : " + I.MemWrite + ", Branch:  " + I.PCtoBranch);
		System.out.println("EX controls :  ImmediateUseForALU : "+ I.UseImmediateForALU);
		break;
		
		
		
		case("1001"):
		case("1010"):
		case("0111"):
		case("1000"):
		I.fillR1();
		I.fillOffset();
		I.fillRd();
		
		
		//TODO: decode into the RegFile
		readreg1ID = I.r1;
		readreg2ID = I.rd;
		
		for(int i = 0 ; i<this.RegFile.registers.length; i++) {
			if(this.RegFile.registers[i].RegID.equals(readreg1ID)) {
				 readReg1 = this.RegFile.registers[i];
			}
			if(this.RegFile.registers[i].RegID.equals(readreg2ID)) {
				 readReg2 = this.RegFile.registers[i];
			}
			
		
		}	
		readData1 = readReg1.theRegister;
		readData2 = readReg2.theRegister;
		RegFile.readData1 = readData1;
		RegFile.readData2 = readData2;
		RegFile.readRegister1 = readReg1;
		RegFile.readRegister2 = readReg2;	
		//ONLY FOR R Type 
		System.out.println("Instruction is an I type");
		//RegFile.writeRegister = writeReg;	 asd
		System.out.println("ReadData1 is : " + RegFile.readData1 +" and in Register of ID " + readReg1.RegID);	
		System.out.println("ReadData2 is : " + RegFile.readData2 +" and in Register of ID " + readReg2.RegID);
		
		
		//TODO: run the Main control evaluator , then assign the booleans into the attributes inside the instruction
		this.MainCont.evaluateSignals(I.op);
		I.WriteBack = MainCont.WriteBack;
		I.UseImmediateForALU = MainCont.UseImmediateForALU;
		I.PCtoBranch = MainCont.PCtoBranch;
		I.MemRead = MainCont.MemRead;
		I.MemWrite = MainCont.MemWrite;
		//I.MemToReg = MainCont.MemToReg;
		
		this.AluCont.opcode = I.op;
		I.ALU4bitoperation = this.AluCont.evaluateALU4Bit();
		I.type = "I";
		//TODO: turn the decoded flag = true
		I.decoded = true;
		System.out.println("WB controls: " + "WriteBack: " +I.WriteBack);
		System.out.println("MEM controls : " + "MemRead: "+I.MemRead + ", MemWrite : " + I.MemWrite + ", Branch:  " + I.PCtoBranch);
		System.out.println("EX controls :  ImmediateUseForALU : "+ I.UseImmediateForALU);
		break;
			
		
		
		case("1011"):
		case("1100"):
			
			I.fillR1();
		I.fillR2();
		I.fillRd();
		I.immediate = I.rd;
		I.rd = "";
		
		
		
		//TODO: decode into the RegFile
		readreg1ID = I.r1;
		readreg2ID = I.r2;
		
		for(int i = 0 ; i<this.RegFile.registers.length; i++) {
			if(this.RegFile.registers[i].RegID.equals(readreg1ID)) {
				 readReg1 = this.RegFile.registers[i];
			}
			if(this.RegFile.registers[i].RegID.equals(readreg2ID)) {
				 readReg2 = this.RegFile.registers[i];
			}
			
		
		}	
		readData1 = readReg1.theRegister;
		readData2 = readReg2.theRegister;
		RegFile.readData1 = readData1;
		RegFile.readData2 = readData2;
		RegFile.readRegister1 = readReg1;
		RegFile.readRegister2 = readReg2;	
		//ONLY FOR R Type 
		System.out.println("Instruction is a beq type");
		//RegFile.writeRegister = writeReg;	 asd
		System.out.println("ReadData1 is : " + RegFile.readData1 +" and in Register of ID " + readReg1.RegID);	
		System.out.println("ReadData2 is : " + RegFile.readData2 +" and in Register of ID " + readReg2.RegID);
		
		
		//TODO: run the Main control evaluator , then assign the booleans into the attributes inside the instruction
		this.MainCont.evaluateSignals(I.op);
		I.WriteBack = MainCont.WriteBack;
		I.UseImmediateForALU = MainCont.UseImmediateForALU;
		I.PCtoBranch = MainCont.PCtoBranch;
		I.MemRead = MainCont.MemRead;
		I.MemWrite = MainCont.MemWrite;
		//I.MemToReg = MainCont.MemToReg;
		
		this.AluCont.opcode = I.op;
		I.ALU4bitoperation = this.AluCont.evaluateALU4Bit();
		I.type = "beq";
		//TODO: turn the decoded flag = true
		I.decoded = true;
		System.out.println("WB controls: " + "WriteBack: " +I.WriteBack);
		System.out.println("MEM controls : " + "MemRead: "+I.MemRead + ", MemWrite : " + I.MemWrite + ", Branch:  " + I.PCtoBranch);
		System.out.println("EX controls :  ImmediateUseForALU : "+ I.UseImmediateForALU);
		break;
		
		
		case("1101"):
			I.fillAddress();
		System.out.println("Instruction is a Jump type");
		this.MainCont.evaluateSignals(I.op);
		I.WriteBack = MainCont.WriteBack;
		I.UseImmediateForALU = MainCont.UseImmediateForALU;
		I.PCtoBranch = MainCont.PCtoBranch;
		I.MemRead = MainCont.MemRead;
		I.MemWrite = MainCont.MemWrite;
		I.Jump = MainCont.Jump;
		//I.MemToReg = MainCont.MemToReg;
		
		this.AluCont.opcode = I.op;
		I.ALU4bitoperation = this.AluCont.evaluateALU4Bit();
		I.type = "J";
		//TODO: turn the decoded flag = true
		I.decoded = true;
		System.out.println("WB controls: " + "WriteBack: " +I.WriteBack);
		System.out.println("MEM controls : " + "MemRead: "+I.MemRead + ", MemWrite : " + I.MemWrite + ", Branch:  " + I.PCtoBranch);
		System.out.println("EX controls :  ImmediateUseForALU : "+ I.UseImmediateForALU);
		break;
		
	}
		
		System.out.println("__________________ ending decoding ________________");
	}
	
	
	public void execute(Instruction I) {
		
		System.out.println("__________________ Starting executing ________________");
		System.out.println(" executing instruction of ID " + I.ID);
		if(I.UseImmediateForALU) {
			this.Alu.output = this.Alu.ALUEvaluator(I.ALU4bitoperation, RegFile.readData1, SignExtend(I));
		}
		else if(!I.Jump) {
			this.Alu.output = this.Alu.ALUEvaluator(I.ALU4bitoperation, RegFile.readData1, RegFile.readData2);
			
			if(I.PCtoBranch) {
				switch(I.op) {
				case("1011"): 
					if(!this.Alu.Z) {
						I.branchingAddress = Integer.toBinaryString(this.IMemory.pc + Integer.parseInt(I.immediate,2));
						System.out.println("Should branch successfuly into addres " + I.branchingAddress +" as they are not equal");
						this.IMemory.pc = Integer.parseInt(I.branchingAddress,2);
						System.out.println("New PC value is " + IMemory.pc);
					}
					else
						System.out.println("branching unsuccseful , 2 operands are equal");
				
				break;
				case("1100"):
					if(this.Alu.output.charAt(0) == '1' && this.Alu.output.length() >=32) {
						System.out.println("branching unsuccseful , op1 < op2");
					
					}
					else {
						I.branchingAddress = Integer.toBinaryString(this.IMemory.pc + Integer.parseInt(I.immediate,2));
					System.out.println("Should branch successfuly into addres " + I.branchingAddress +" as op1 > op2");
					this.IMemory.pc = Integer.parseInt(I.branchingAddress,2);
					System.out.println("New PC value is " + IMemory.pc);
					}
				
				break;
				}
			}
		}
		if(I.Jump) {
			System.out.println("Jumping to Address " + I.address);
			this.IMemory.pc = Integer.parseInt(I.address,2);
			System.out.println("New PC value is " + IMemory.pc);
			
		}
		I.ALUOUT = this.Alu.output;
		I.executed = true;
		System.out.println("__________________ ending executing ________________");
	}
	
	
	public void memAccess(Instruction I) {
		
		
		System.out.println("__________________ Starting MemoryAccess ________________");
		System.out.println(" MemoryAccessing instruction of ID " + I.ID);
		DMemory.MemRead = I.MemRead;
		DMemory.MemWrite = I.MemWrite;
		
		if(DMemory.MemRead) {
			this.Cache.retrieveFromCache(I.ALUOUT);
			
			
		}
		if(DMemory.MemWrite) {
			Cache.cache[Integer.parseInt(I.ALUOUT, 2)].data = RegFile.readData2;
			DMemory.writeDataIntoAddress(I.ALUOUT, RegFile.readData2);
		}
		
		I.accessed = true;
		System.out.println("__________________ ending MemoryAccess ________________");
	}
	
	public void writeBack(Instruction I) {
		//during the wb , I need to get the id of the regs used in the instruction, then write back to it
		
		System.out.println("__________________ Starting WriteBack ________________");
		System.out.println(" WritingBack instruction of ID " + I.ID);
		if(I.type.equals("R") && I.WriteBack) {
			System.out.println("using the rd register for writeback");
			if(I.MemRead) {
				RegFile.registers[Integer.parseInt(I.rd, 2)].theRegister = DMemory.ReadData;
				//RegFile.writeRegister.theRegister = 
				RegFile.writeData = DMemory.ReadData;
			}
	else {
				
				RegFile.registers[Integer.parseInt(I.rd, 2)].theRegister = I.ALUOUT;
				//RegFile.writeData = RegFile.writeRegister.theRegister;
				System.out.println("Took the Write Value from ALU directly and not from data memory ");
				System.out.println("The written data is in reg " + RegFile.registers[Integer.parseInt(I.rd, 2)].RegID + " and the data is " + I.ALUOUT);

			}
		}
		else {
			if(!I.PCtoBranch)
			System.out.println("Using the r2 Register for Writeback");
			if(I.MemRead) {
				//we will write into ReadReg2 the data in ReadDAta from the DataMemoryClass
				RegFile.registers[Integer.parseInt(I.rd, 2)].theRegister = DMemory.ReadData;
				//RegFile.readData2 = DMemory.ReadData;
				System.out.println("The written data is in reg " + RegFile.registers[Integer.parseInt(I.rd, 2)].RegID + " and the data is " + 
				RegFile.registers[Integer.parseInt(I.rd, 2)].theRegister);
			}
			else if(!I.Jump){
				RegFile.registers[Integer.parseInt(I.rd, 2)].theRegister = I.ALUOUT;
				System.out.println("The written data is in reg " + RegFile.registers[Integer.parseInt(I.rd, 2)].RegID + " and the data is " + 
						RegFile.registers[Integer.parseInt(I.rd, 2)].theRegister);
				
			}
		}
		
		I.writtenback = true;
		System.out.println("__________________ ending WriteBack ________________");
	}
	
	public void shiftRightTest() throws Exception {
		IMemory.loadIntoInstructionMemory("0101100001110001");		//shift right on $8 using value from %7 and write into &1
		RegFile.registers[8].theRegister = "1111";
		RegFile.registers[7].theRegister = "010";
		System.out.println("Value in reg 1 is " + RegFile.registers[1].theRegister);
	}
	
	public void shiftleftTest() throws Exception {
		IMemory.loadIntoInstructionMemory("0100100001110001");      //shift left on $8 using value from %7 and write into &1
		RegFile.registers[8].theRegister = "1111";
		RegFile.registers[7].theRegister = "010";
		System.out.println("Value in reg 1 is " + RegFile.registers[1].theRegister);
	}
}

