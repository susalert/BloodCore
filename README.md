# BloodCore

BloodCore is a ProjectKorra addon API that centralizes player interaction and lifecycle events so ability classes do not need their own listeners.

## Quick Usage

Implement `me.susalert.bloodcore.api.Interactable` on your ability and override only the hooks you need.

```java
public class BloodExample extends BloodAbility implements AddonAbility, Interactable {

    @Override
    public void onSneakStart(Player player) {
        // Shift-down behavior
    }

    @Override
    public void onSwing(Player player) {
        // Left-click/swing behavior
    }

    @Override
    public boolean onDamageTaken(EntityDamageEvent event) {
        // Return true to cancel/absorb damage
        return false;
    }
}
```

## Routed Hooks

- Input: swing, right-click, sneak start/end, slot change, offhand swap, item drop
- Lifecycle: ProjectKorra reload, ability end, bind change
- Combat: entity damage handling
