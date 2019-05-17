package struct;

import java.util.ArrayList;
import java.util.List;

public class ClassCodeItem {
	
//	struct DexCode {
//	    u2 registersSize;   /* 使用的寄存器个数 */
//	    u2 insSize;         /* 参数个数 */
//	    u2 outsSize;        /* 调用其他方法时其它方法使用的寄存器个数，会在自己的调用栈申请，并压栈（猜测） */
//	    u2 triesSize;       /* Try/Catch个数 */
//	    u4 debugInfoOff;    /* 指向调试信息的偏移 */
//	    u4 insnsSize;       /* 指令集个数，以2字节为单位 */
//	    u2 insns[1];        /* 指令集 */
//	};
	public short registersSize;
	public short insSize;
	public short outsSize;
	public short triesSize;
	public int debugInfoOff;
	public int insnsSize;
	public List<Short> insns = new ArrayList<Short>();
	
}
