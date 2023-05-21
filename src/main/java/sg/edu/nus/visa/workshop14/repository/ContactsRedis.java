package sg.edu.nus.visa.workshop14.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import jakarta.validation.Valid;
import sg.edu.nus.visa.workshop14.model.Contact;
import org.springframework.data.redis.core.RedisTemplate;

@Repository
public class ContactsRedis {

    final String CONTACT_LIST = "contactlist";    

    @Autowired
    RedisTemplate<String, Object> template; 
    
    public void saveContact(@Valid Contact contact, Model model) {
        //To insert record in list
        //template.opsForList().leftPush(CONTACT_LIST, contact.getId());

        //To insert record in hash(map)
        template.opsForHash().put(CONTACT_LIST+"_HASH", contact.getId(), model);
        model.addAttribute("contact", contact);
    }

    public Contact getContactById(String contactId) {
        Contact contact = (Contact) template.opsForHash().get(CONTACT_LIST+"_HASH", contactId);
        return contact;
    }

    public List<Contact> getAllContacts(Model model){
        return template.opsForHash().values(CONTACT_LIST+"_HASH")
        .stream()
        .filter(Contact.class::isInstance)
        .map(Contact.class::cast)
        .collect(Collectors.toList()); 
        //Data would be converted to a stream, then we want to convert it back to a list 
    }

    
}
