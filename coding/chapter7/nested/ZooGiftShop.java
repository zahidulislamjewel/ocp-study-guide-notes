public class ZooGiftShop {
    abstract class SaleTodayOnly {
        abstract int dollarsOff();
    }

    interface TotalSale {
        int dollarsOff();
    }

    public int admission(int basePrice) {
        SaleTodayOnly saleToday = new SaleTodayOnly() {

            @Override
            int dollarsOff() {
                return 3;
            }
        };
        return basePrice - saleToday.dollarsOff();
    }

    public int profit(int buyingPrice) {
        TotalSale totalSale = new TotalSale() {

            @Override
            public int dollarsOff() {
                return 50;
            }
        };

        return totalSale.dollarsOff() - buyingPrice;
    }

    public int loss(int buyingPrice) {
        TotalSale totalSale = () -> 40;
        
        return totalSale.dollarsOff() - buyingPrice;
    }
}

class ZooGiftShopTest {
    public static void main(String[] args) {
        ZooGiftShop zooGiftShop = new ZooGiftShop();
        System.out.println(zooGiftShop.admission(10));
        System.out.println(zooGiftShop.profit(30));
        System.out.println(zooGiftShop.loss(30));
    }
}
