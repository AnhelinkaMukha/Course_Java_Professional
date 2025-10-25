package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private NavigableMap<Customer, String> custMap;

    CustomerService() {
        Comparator<Customer> byIdComparator = Comparator.comparingLong(Customer::getScores);

        custMap = new TreeMap<>(byIdComparator);
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = custMap.firstEntry();
        return copyEntry(entry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        if (!custMap.containsKey(customer)) {
            custMap.put(customer, null);
        }
        return copyEntry(custMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        custMap.put(customer, data);
    }

    private Map.Entry<Customer, String> copyEntry(Map.Entry<Customer, String> e) {
        if (e == null) return null;
        Customer c = e.getKey();
        Customer copy = new Customer(c.getId(), c.getName(), c.getScores());
        return Map.entry(copy, e.getValue());
    }
}
