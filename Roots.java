
public class Roots {

	private static double[] f = {2, -11.7, 17.7, -5};
	
	public static void main(String[] args){
		System.out.println("Bisection Method:");
		bisection(f, 0, 4);
		System.out.println("-------------------------------");
		System.out.println("False Position Method:");
		falsePosition(f, 0, 4);
		System.out.println("-------------------------------");
		System.out.println("Newton Method:");
		newton(f, 3);
		System.out.println("-------------------------------");
		System.out.println("Secant Method: ");
		secant(f, 2, 4);
		System.out.println("-------------------------------");
		System.out.println("Modified Secant Method: ");
		modifiedSecant(f, .01, 2);
	}
	
	private static double calc(double[] func, double x){
		int n = func.length - 1;
		double p = func[n];
		for(int i = n - 1; i >= 0; --i){
			p = func[i] + x*p;
		}
		return p;
	}
	
	public static void bisection(double[] f, double a, double b){
		double c, fc;
		double fa = calc(f, a);
		double fb = calc(f, b);
		if(fa * fb > 0){
			return;
		}
		double previous;
		double error = b - a;
		for(int i = 0; i < 100; ++i){
			System.out.println("Iteration " + i + ": ");
			error /= 2;
			System.out.println("Error: " + error);
			c = a + error;
			previous = c + error;
			System.out.println("a = " + a);
			System.out.println("b = " + b);
			System.out.println("c = " + c);
			fc = calc(f, c);
			System.out.println("f(a) = " + fa);
			System.out.println("f(b) = " + fb);
			System.out.println("f(c) = " + fc);
			
			if(Math.abs(error) < (0.5*Math.pow(10, -5))){
				System.out.println("The margin of error is small enough");
				return;
			}
			if(fa * fc < 0){
				b = c;
				fb = fc;
			}else{
				a = c;
				fa = fc;
			}
			if(i > 0){
				System.out.println("Percent error: " + Math.abs(c - previous)/100);
			}
		}
	}
	
	public static void falsePosition(double[] f, double a, double b){
		double c, fc;
		double fa = calc(f, a);
		double fb = calc(f, b);
		if(fa * fb > 0){
			return;
		}
		double error = b - a;
		double previous;
		for(int i = 0; i < 100; ++i){
			System.out.println("Iteration " + i + ": ");
			error /= 2;
			System.out.println("Error: " + error);
			c = findC(f, a, b);
			previous = c + error;
			System.out.println("a = " + a);
			System.out.println("b = " + b);
			System.out.println("c = " + c);
			fc = calc(f, c);
			System.out.println("f(a) = " + fa);
			System.out.println("f(b) = " + fb);
			System.out.println("f(c) = " + fc);

			if(Math.abs(error) < (0.5*Math.pow(10, -5))){
				System.out.println("The margin of error is small enough");
				return;
			}
			if(fa * fc < 0){
				b = c;
				fb = fc;
			}else{
				a = c;
				fa = fc;
			}
			if(i > 0){
				System.out.println("Percent error: " + Math.abs(c - previous)/100);
			}
		}
	}
	
	private static double findC(double[] f, double a, double b){
		double fa = calc(f, a);
		double fb = calc(f, b);
		double c = (a*fb - b*fa)/(fb - fa);
		return c;
	}
	
	public static void newton(double[] f, double x0){
		double[] fprime = derivative(f);
		double fx, fp, d;
		fx = calc(f, x0);
		double previous;
		for(int i = 0; i < 100; ++i){
			System.out.println("Iteration " + i + ": ");
			System.out.println("x = " + x0);
			System.out.println("f(x) = " + fx);
			fp = calc(fprime, x0);
			System.out.println("f'(x) = " + fp);
			if(Math.abs(fp) < (0.5*Math.pow(10, -10))){
				return;
			}
			d = fx/fp;
			previous = calc(f, x0 + d)/calc(fprime, x0 + d);
			if(i > 0){
				System.out.println("Percent error: " + Math.abs(d - previous)/100);
			}
			System.out.println("Error: " + d);
			if(Math.abs(d) < (0.5*Math.pow(10, -5))){
				System.out.println("The margin of error is small enough");
				return;
			}
			x0 -= d;
			fx = calc(f, x0);
		}
		
	}
	
	private static double[] derivative(double[] f){
		double[] fprime = new double[f.length - 1];
		for(int i = 1; i < f.length; ++i){
			fprime[i - 1] = f[i] * i;
		}
		return fprime;
	}
	
	public static void secant(double[] f, double a, double b){
		double fa, fb, d, previous;
		fa = calc(f, a);
		fb = calc(f, b);
		if(Math.abs(fa) > Math.abs(fb)){
			swap(a, b);
			swap(fa, fb);
		}
		for(int i = 1; i < 100; ++i){
			System.out.println("Iteration " + i + ": ");
			if(Math.abs(fa) > Math.abs(fb)){
				swap(a, b);
				swap(fa, fb);
			}
			d = (b - a)/(fb - fa);
			System.out.println("x = " + a);
			System.out.println("f(x) = " + fa);
			b = a;
			fb = fa;
			d = d * fa;
			if(i > 1){
				previous = d/fa;
				System.out.println("Percent error: " + Math.abs(d - previous)/100);
			}
			System.out.println("Error: " + d);
			if(Math.abs(d) < (0.5*Math.pow(10, -5))){
				System.out.println("The margin of error is small enough");
				return;
			}
			a -= d;
			fa = calc(f, a);
		}
	}
	
	private static void swap(double a, double b){
		double temp = a;
		a = b;
		b = temp;
	}

	public static void modifiedSecant(double[] f, double S, double b){
		double fS, fb, fbS, d, previous;
		fS = calc(f, S);
		fb = calc(f, b);
		double x = b+(S*b);
		fbS = calc(f, x);
		for(int i = 1; i < 5; ++i){
			System.out.println("Iteration " + i + ": ");
			d = (S*fb)/(fbS - fb);
			System.out.println("x = " + S);
			System.out.println("f(x) = " + fS);
			b = S;
			fb = fS;
			d = d * fS;
			if(i > 1){
				previous = d/fS;
				System.out.println("Percent error: " + Math.abs(d - previous)/100);
			}
			System.out.println("Error: " + d);
			if(Math.abs(d) < (0.5*Math.pow(10, -5))){
				System.out.println("The margin of error is small enough");
				return;
			}
			S -= d;
			fS = calc(f, S);
		}
	}
}
