package assign_2;

import java.util.ArrayList;

public class Matrix  {
	 public int[][] numbers;
	 public int N;
	 public int M;

	public int[][] getNumbers() {
		return numbers;
	}
	public boolean setNumbers(ArrayList<Integer> num) 
	{
		if(num.size()!=N*M) {
			return false ;
		}
		int k=0;
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				numbers[i][j]=num.get(k);
				k++;
			}
		}
		
		return true;
		
	}
	
	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
	}
	public int getM() {
		return M;
	}
	public void setM(int m) {
		M = m;
	}
	
	//Constructor for the class
	public Matrix( int m, int n) {
		this.numbers = new int[m][n];
		this.N = n;
		this.M = m;
	}
	
	public void Print() {
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(numbers[i][j]+"\t");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
		
	}
	
	public void Transpose() {
		int[][] numb =new int[M][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				numb[j][i]=numbers[i][j];
			}
			
		}
		int temp=N;
		N=M;
		M=temp;
		numbers=numb;	
	}
	
	Matrix multiply(Matrix B) throws MultiplicationException {
		//Checks for exception messages
		if ( (this.N<=0) || (this.M <=0)){
			MultiplicationException error = new MultiplicationException("error: the caller matrix's dimensions are wrong");
			throw error;
		}
		if ((B.N<=0) || (B.M <=0)){
			MultiplicationException error1 = new MultiplicationException("error: matrix B's dimensions are wrong");
			throw error1;
		}
		
		if(this.N!=B.M) {
			MultiplicationException error2 = new MultiplicationException("Exception occured while trying to multiply two matrices of dimensions ("+this.M+","+this.N+") and ("+B.M+","+B.N+")");
			throw error2;
		}
		
		
		//m*n X n*z = m*z
		Matrix result =new Matrix(this.M,B.N);
		for (int i=0; i<this.M; i++)
		{
			for (int j=0; j<B.N; j++) {
				result.numbers[i][j] = 0;
				for (int l=0; l<B.M; l++) {
					result.numbers[i][j] += this.numbers[i][l] * B.numbers[l][j];
				}
			}
		}
		return result;
	}
	
	
	public static final class MultiplicationException extends Exception {
		public String errorMessage;
	
		public MultiplicationException(String errorMessage) {
			//used to invoke the parent class constructor.
			//So that the Message function is shown when the object is thrown
			super();
			this.errorMessage = errorMessage;
		}
		 public void Message() {
			 System.out.print(this.errorMessage+"\n");
		}
		
	}
	 
	 
}
