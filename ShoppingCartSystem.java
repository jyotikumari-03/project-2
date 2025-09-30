import java.util.ArrayList;
import java.util.Scanner;

// Product class with encapsulation
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getTotalPrice(){
        return price * quantity;
    }
}

// Discount interface with polymorphism
abstract class Discount {
    abstract double applyDiscount(ArrayList<Product> products);
}

// Festive discount: 10% off total
class FestiveDiscount extends Discount {
    @Override
    double applyDiscount(ArrayList<Product> products){
        double total = 0;
        for(Product p : products){
            total += p.getTotalPrice();
        }
        return total * 0.9; // 10% off
    }
}

// Bulk discount: 20% off if quantity > 5 (per product)
class BulkDiscount extends Discount {
    @Override
    double applyDiscount(ArrayList<Product> products){
        double total = 0;
        for(Product p : products){
            if(p.getQuantity() > 5){
                total += p.getTotalPrice() * 0.8; // 20% off
            } else {
                total += p.getTotalPrice();
            }
        }
        return total;
    }
}

// Payment interface
interface Payment {
    void pay(double amount);
}

// Concrete payment implementation
class PaymentImpl implements Payment {
    @Override
    public void pay(double amount){
        System.out.printf("Total Amount Payable: %.2f\n", amount);
    }
}

public class ShoppingCartSystem {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());

        ArrayList<Product> cart = new ArrayList<>();

        for(int i=0; i<n; i++){
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            String name = parts[0];
            double price = Double.parseDouble(parts[1]);
            int qty = Integer.parseInt(parts[2]);
            cart.add(new Product(name, price, qty));
        }

        String discountType = sc.nextLine().trim();

        // Print products
        for(Product p : cart){
            System.out.printf("Product: %s, Price: %.2f, Quantity: %d\n", p.getName(), p.getPrice(), p.getQuantity());
        }

        Discount discount;
        if(discountType.equalsIgnoreCase("festive")){
            discount = new FestiveDiscount();
        } else if(discountType.equalsIgnoreCase("bulk")){
            discount = new BulkDiscount();
        } else {
            // No discount
            discount = new Discount(){
                @Override
                double applyDiscount(ArrayList<Product> products){
                    double total = 0;
                    for(Product p : products){
                        total += p.getTotalPrice();
                    }
                    return total;
                }
            };
        }

        double finalAmount = discount.applyDiscount(cart);

        Payment payment = new PaymentImpl();
        payment.pay(finalAmount);

        sc.close();
    }
}
