        
// This is the basic Matrix class of GroovyLab.
// Ιt has a lot of operations covering basic mathematical tasks.
// Also, it offers an extensive range of static operations that help to perform conveniently many things.
// Convenient syntax is offered using Groovy's freatures.
// Also, some native optimized C libraries and NVIDIA CUDA support is offered for faster maths.

package groovySci.math.array;

import groovy.lang.GroovyObjectSupport;
import groovy.lang.IntRange;


/** Matrix class to provide similar behavior that high level math languages like MATLAB, Scilab, R, ...

The class implements the GroovyObjectSupport interface, therefore it provides a lot of the syntactic sugar
that Groovy permits. However, the implementations are in Java, therefore there attain maximum 
execution speed.

	
  Some of the available operators:          Matrix + Matrix,         Matrix + Number,        Matrix - Matrix,
   Matrix - Number,         Matrix * Matrix,         Matrix * Number,
   Matrix / Matrix,         Matrix / Number,        Matrix ** int"
		
   Some of the static operations are: 
        sum(Matrix),        prod(Matrix),        cumsum(Matrix),
        cumprod(Matrix),        inverse(Matrix),        inv(Matrix),
        solve(Matrix A, Matrix b)	//returns X Matrix verifying A*X = b. 
        rank(Matrix),        trace(Matrix),         det(Matrix)
        cond(Matrix),        norm1(Matrix),        norm2(Matrix)
        normF(Matrix),        normInf(Matrix),        dot(Matrix A, Matrix b)  // dot product of the two matrices
		
   Some of the the JAMA based static methods for Linear Algebra are:
        Cholesky decomposition:,         CholeskyL(Matrix),        CholeskySPD(Matrix)
        QR decomposition,        QR_Q(Matrix),        QR_H(Matrix)
        QR_R(Matrix),        LU decomposition:,        LU_L(Matrix)
        LU_U(Matrix),        LU_P(Matrix),
     Singular values decomposition:
        svd_S(Matrix),        svd_U(Matrix),        svd_V(Matrix),        svd_values(Matrix) 
     Eigenvalues decomposition:
        eig_D(Matrix),        eig_V(Matrix)

 //  computes the eigenvalues and eigenvectors of a real matrix
        //  the return matrix is as follows:
        //       column 0:    the real parts of the eigenvalues
        //       column 1:    the imaginary parts of the eigenvalues
        //       columns   n..2+n-1   :  the real parts of the eigenvectors
        //       columns   2+n..2+n+n  : the imaginary parts of the eigenvectors
        public static Matrix eig(Matrix M)

	
             Some of the available constructors:
    Matrix(double[][])
    Matrix(double[])	// one row Matrix constructor
    Matrix(ArrayList)	// compatible with ArrayList of Numbers or ArrayList of ArrayList of Numbers
	
    identity(int n)                  // identity Matrix of size n*n alias to id(int n)
    diagonal(int, double)	// diagonal Matrix of constant values, alias to diag(int, double)
    diagonal(double[])	// diagonal Matrix with given diagonal values, alias to diag(double[])
    one(int, int)		// constant Matrix of given size, filled with 1.0 values 
    ones(int, int)	// constant Matrix of given size, filled with 1.0 values 
    ones(int)		// constant Matrix of given size, filled with 1.0 values 
    zeros(int, int)	// constant Matrix of given size, filled with 0.0 values 
    zeros(int, int)	// constant Matrix of given size, filled with 0.0 values 
    zeros(int)          	// constant Matrix of given size, filled with 0.0 values 
	
    fill(int, int, double)	// constant Matrix of given size, filled with given values 
    increment(int, int, double begin, double pitch) // Matrix of given size with row incrementing values from given beginning value wsith given pitch increment
    increment(int, int, double[] begin, double[] pitch) // Matrix of given size with row incrementing values from given beginning values with given pitchs increment

 Following statistic sample constructors are available (random generator from RngPack):
    random(int, int)								// independent random values (between 0.0 and 1.0) Matrix of given size, alias to rand(int, int)
    random(int, int, double min, double max)		// independent random values (between min and max) Matrix of given size, alias to rand(int, int, double min, double max)
    randomUniform(int m, int n, double min, double max)  
    randomDirac(int m, int n, double[] values, double[] prob)  
    randomNormal(int m, int n, double mu, double sigma)  
    randomChi2(int m, int n, int d)  
    randomLogNormal(int m, int n, double mu, double sigma)  
    randomExponential(int m, int n, double lambda)  
    randomTriangular(int m, int n, double min, double max)  
    randomTriangular(int m, int n, double min, double med, double max)  
    randomBeta(int m, int n, double a, double b)  
    randomCauchy(int m, int n, double mu, double sigma)  
    randomWeibull(int m, int n, double lambda, double c)  
	
Following static sort/find methods are available:
    sort(Matrix)
    sort(Matrix, int columnIndex)
    min(Matrix)
    max(Matrix)
	
Following static transformation methods are available:
    transpose(Matrix)	// alias to t(Matrix)
    resize(Matrix, int, int)
    rowsMatrix >> Matrix	// appends rowsMatrix to Matrix at last position (i.e. add last row)
    columnsMatrix >>> Matrix	// appends columnsMatrix to Matrix at last position (i.e. add last column)
    Matrix << rowsMatrix	// appends rowsMatrix to Matrix at first position (i.e. add first row)
	
 Following static statistic sample methods are available:
    mean(Matrix)
    variance(Matrix)
    covariance(Matrix,Matrix)
    correlation(Matrix,Matrix)
 * 
 * 
*/

public class MatR1D  extends GroovyObjectSupport  {

    public int Nrows, Ncols;
    private  static MatR1D    extractedMatrixObject;   // used for cascading calls at indexing using IntRanges
    // e.g. x = rand(30,30)
    // after row extraction, extractedMatrixObject points to the Matrix x[1..2]
    // thus, [3..4] is interpreted as column range instead of row
    // y = x[1..2][3..4]  
    
    // The storage of data as double [][] array. We can manipulate it directly for efficiency.
     public    double[] d;

     // get the data array by reference
     final public  double [] getArray() { return d; } 
   
        // gets the first row of the matrix as double [] array 
     final public double [] getv() {
            int ncol = Ncols; // number of columns
            double [] firstRow =  new double[ncol];
            for (int c=0; c<ncol; c++)
                firstRow[c] = d[c];
            
            return firstRow;
        }

final public int numRows()  { return Nrows; }
final public int numCols() { return Ncols; }
final public int numColumns() { return Ncols; }
 
// returns size as an array of two ints, i.e. int[2]
final public int  [] size() {
    int [] siz = new int[2];
    siz[0] = Nrows;
    siz[1] = Ncols;
    return  siz;
}
	
final public  static int[] size(MatR1D M) {
    return M.size();
}

       // set the reference to double[][] storage of this matrix object to a different array 	
   final  public void setRef(double[] a) {
        d = a;
    }

   
   
   
   
     
    // get  the data array by value
final public double [][] toDoubleArray() { 
    double [][] data = new double[Nrows][Ncols];
   
    int cnt=0;
    for (int r = 0; r < Nrows; r++)
        for (int c=0; c < Ncols; c++ )
            data[r][c] = d[cnt++];
    return data;
}

 
    
  

public MatR1D(int n, int m)  {  // creates a zero Matrix 
    d = new double[n*m];
         
    Nrows = n;
    Ncols = m;
}


public MatR1D(int n, int m, double v)  {  // creates a Matrix filled with the value
   d = new double[n*m];
         
    Nrows = n;
    Ncols = m;
    
    for (int k=0; k<n*m; k++)
        d[k]=v;
}

public MatR1D(MatR1D m) {    // matrix to copy
 d = DoubleArray.copy(m.d);   
 
 Nrows = m.Nrows;
 Ncols = m.Ncols;
}

final public double get(int row, int col) {
   return  d[row*Nrows+col];
}
	
final public double[] getRef() {
   return d;
}
	
	
final  public void set(int row, int col,  Number v) {
    d[row*Nrows+col] = v.doubleValue();
}
	
final public void set(int row, int col, Integer v) {
     d[row*Nrows+col] = (double) v.intValue();
}




// this method is used to overload the indexing operator e.g.,
// A = rand(8, 9);   a23 = A[2,3]
final public double   getAt(int row, int col)  { 
    return d[row*Nrows+col];
}




final public void   putAt(int row, int col, double value)  { 
    d[row*Nrows+col] = value;
            
}

 final public MatR1D plus(MatR1D v2) {
        
   MatR1D r = new MatR1D(Nrows, Ncols);
   for (int k=0; k<Nrows*Ncols; k++)
       r.d[k] = d[k]+v2.d[k];
   
   return r;
    }

 final public MatR1D plus(double [] v2) {
        
   MatR1D r = new MatR1D(Nrows, Ncols);
   for (int k=0; k<Nrows*Ncols; k++)
       r.d[k] = d[k]+v2[k];
   
   return r;
    }

 
 final public MatR1D minus(MatR1D v2) {
        
   MatR1D r = new MatR1D(Nrows, Ncols);
   for (int k=0; k<Nrows*Ncols; k++)
       r.d[k] = d[k]-v2.d[k];
   
   return r;
    }

 final public MatR1D minus(double [] v2) {
        
   MatR1D r = new MatR1D(Nrows, Ncols);
   for (int k=0; k<Nrows*Ncols; k++)
       r.d[k] = d[k]-v2[k];
   
   return r;
    }

 final public void  putAt(java.util.List<Object>  rc, double value) {
    if (rc.get(0) instanceof  Integer)
         d[(int)rc.get(0)*Nrows+(int)rc.get(1)] = value;
 }

}