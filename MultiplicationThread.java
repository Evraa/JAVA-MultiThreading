package assign_2;
import java.lang.Math; 


//import assign_2.Matrix.MultiplicationException;

public class MultiplicationThread implements Runnable  {
	//Data members
	Matrix A;
	Matrix B;
	Matrix result;
	
	public void setA (Matrix a) {
		this.A = null;
		this.A = a;
		}
	public void setB (Matrix b) {
		this.B = null;
		this.B = b;
		}
	public void setResult() {
		this.result = null;
		this.result = new Matrix(this.A.M,this.B.N);
//		this.result.setM(this.A.M);
//		this.result.setN(this.B.N);
	}
	//Constructor
	public MultiplicationThread(Matrix a,Matrix b) {
		super();
		this.A = a;
		this.B = b;
		//This is not required!
		//this.result = this.A.multiply(this.B);
		this.result = new Matrix(A.M,B.N);
	}
	
	void sub_multiply(Matrix B,int begin,int end) {
		//Matrix C =new Matrix(end,B.N);
		for (int i=begin; i<end; i++)
		{
			for (int j=0; j<B.N; j++) {
				this.result.numbers[i][j] = 0;
				for (int l=0; l<B.M; l++) {
					this.result.numbers[i][j] += this.A.numbers[i][l] * B.numbers[l][j];
				}
			}
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Assuming we've named them 1 and 2
		if (Thread.currentThread().getName().equals("1")) {
			//first thread
			//Multiply A[0,(A.m/2),:] by B and place in result upper part
			sub_multiply(this.B,0,(this.A.getM()/2));
			
		}else if (Thread.currentThread().getName().equals("2")) {
			//second thread
			//Multiply A[(A.m/2)+1,:,:] by B and place in result lower part
			sub_multiply(this.B,(this.A.getM()/2),this.A.getM());
		}else {
			System.out.println("An error occured, thread name is: "+Thread.currentThread().getName());
			return;
		}
	}
	
	public void print_result() {
		this.result.Print();
	}
}
