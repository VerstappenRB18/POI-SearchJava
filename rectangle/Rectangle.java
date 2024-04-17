package rectangle;

class Point {
    private double x;
    private double y;
    private Object userData;

    public Point(double x, double y, Object data) {
        this.x = x;
        this.y = y;
        this.userData = data;
    }

    public double sqDistanceFrom(Point other) {
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        return dx * dx + dy * dy;
    }

    public double distanceFrom(Point other) {
        return Math.sqrt(this.sqDistanceFrom(other));
    }

    // Getters for the properties
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Object getUserData() {
        return userData;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }
}


public class Rectangle {
    private double x, y; // Center of the rectangle
    private double w, h; // Width and height of the rectangle
    private double left, right, top, bottom; // Edges of the rectangle

    public Rectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.left = x - w / 2;
        this.right = x + w / 2;
        this.top = y - h / 2;
        this.bottom = y + h / 2;
    }

    public boolean contains(Point point) {
        return this.left <= point.getX() && point.getX() <= this.right &&
                this.top <= point.getY() && point.getY() <= this.bottom;
    }

    public boolean intersects(Rectangle range) {
        return !(this.right < range.left || range.right < this.left ||
                this.bottom < range.top || range.bottom < this.top);
    }

    public Rectangle subdivide(String quadrant) {
        return switch (quadrant) {
            case "ne" -> new Rectangle(this.x + this.w / 4, this.y - this.h / 4, this.w / 2, this.h / 2);
            case "nw" -> new Rectangle(this.x - this.w / 4, this.y - this.h / 4, this.w / 2, this.h / 2);
            case "se" -> new Rectangle(this.x + this.w / 4, this.y + this.h / 4, this.w / 2, this.h / 2);
            case "sw" -> new Rectangle(this.x - this.w / 4, this.y + this.h / 4, this.w / 2, this.h / 2);
            default -> throw new IllegalArgumentException("Invalid quadrant specified");
        };
    }

    public double xDistanceFrom(Point point) {
        if (this.left <= point.getX() && point.getX() <= this.right) {
            return 0;
        }

        return Math.min(
                Math.abs(point.getX() - this.left),
                Math.abs(point.getX() - this.right)
        );
    }

    public double yDistanceFrom(Point point) {
        if (this.top <= point.getY() && point.getY() <= this.bottom) {
            return 0;
        }

        return Math.min(
                Math.abs(point.getY() - this.top),
                Math.abs(point.getY() - this.bottom)
        );
    }

    public double sqDistanceFrom(Point point) {
        double dx = this.xDistanceFrom(point);
        double dy = this.yDistanceFrom(point);
        return dx * dx + dy * dy;
    }

    public double distanceFrom(Point point) {
        return Math.sqrt(this.sqDistanceFrom(point));
    }

    public static void main(String[] args) {
        // Create a rectangle at center (10, 10) with width 20 and height 20
        Rectangle rect = new Rectangle(10, 10, 20, 20);

        // Create points to test containment
        Point insidePoint = new Point(15, 15, null);  // Inside the rectangle
        Point outsidePoint = new Point(35, 25, null); // Outside the rectangle
        Point boundaryPoint = new Point(10, 10, null); // On the boundary

        System.out.println("Does the rectangle contain insidePoint? " + rect.contains(insidePoint));
        System.out.println("Does the rectangle contain outsidePoint? " + rect.contains(outsidePoint));
        System.out.println("Does the rectangle contain boundaryPoint? " + rect.contains(boundaryPoint));

        // Create another rectangle to test intersection
        Rectangle intersectingRect = new Rectangle(15, 15, 10, 10); // Intersects with rect
        Rectangle nonIntersectingRect = new Rectangle(50, 50, 5, 5); // Does not intersect

        System.out.println("Does rect intersect with intersectingRect? " + rect.intersects(intersectingRect));
        System.out.println("Does rect intersect with nonIntersectingRect? " + rect.intersects(nonIntersectingRect));

        // Subdividing rectangle
        Rectangle neSubRect = rect.subdivide("ne");
        Rectangle nwSubRect = rect.subdivide("nw");
        Rectangle seSubRect = rect.subdivide("se");
        Rectangle swSubRect = rect.subdivide("sw");

        System.out.println("Northeast Subdivision: (" + neSubRect.x + ", " + neSubRect.y + ")");
        System.out.println("Northwest Subdivision: (" + nwSubRect.x + ", " + nwSubRect.y + ")");
        System.out.println("Southeast Subdivision: (" + seSubRect.x + ", " + seSubRect.y + ")");
        System.out.println("Southwest Subdivision: (" + swSubRect.x + ", " + swSubRect.y + ")");
    }
}
