//cube(size=1);
downHolder();
translate(v = [19.4, 0, 0])downHolder();
translate(v = [2, -2, 0])cube(size = [15,4,2]);

module downHolder(){

	difference(){
		union(){
			cylinder(h = 3, r=8.5/2);
			cylinder(h = 2, r=6);
		}
		cylinder(h = 4, r=2);
	}
	;


}