package ar.franciscoruiz.shared.infrastructure.bus.event;

import ar.franciscoruiz.shared.domain.Service;
import ar.franciscoruiz.shared.domain.Utils;
import ar.franciscoruiz.shared.domain.bus.event.DomainEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public final class DomainEventsInformation {
    HashMap<String, Class<? extends DomainEvent>> indexedDomainEvents;

    public DomainEventsInformation() {
        String[]                          packages    = Utils.getGroupId();
        Reflections                       reflections = new Reflections(String.format("%s.%s", packages[0], packages[1]));
        Set<Class<? extends DomainEvent>> classes     = reflections.getSubTypesOf(DomainEvent.class);

        try {
            indexedDomainEvents = formatEvents(classes);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Class<? extends DomainEvent> forName(String name) {
        return indexedDomainEvents.get(name);
    }

    public String forClass(Class<? extends DomainEvent> domainEventClass) {
        return indexedDomainEvents.entrySet()
            .stream()
            .filter(entry -> Objects.equals(entry.getValue(), domainEventClass))
            .map(Map.Entry::getKey)
            .findFirst().orElse("");
    }

    private HashMap<String, Class<? extends DomainEvent>> formatEvents(
        Set<Class<? extends DomainEvent>> domainEvents
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        HashMap<String, Class<? extends DomainEvent>> events = new HashMap<>();

        for (Class<? extends DomainEvent> domainEvent : domainEvents) {
            DomainEvent nullInstance = domainEvent.getConstructor().newInstance();

            events.put((String) domainEvent.getMethod("eventName").invoke(nullInstance), domainEvent);
        }

        return events;
    }
}
