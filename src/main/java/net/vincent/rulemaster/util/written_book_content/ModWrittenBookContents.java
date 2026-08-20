package net.vincent.rulemaster.util.written_book_content;

import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.component.WrittenBookContent;

import java.util.List;

import static net.vincent.rulemaster.util.misc.BaseWrittenBookItem.createPageLinkWithoutHover;

public class ModWrittenBookContents {
    public static class LivoGuideBook {

        public static final String BOOK_TITLE = "Livo's Bane";
        public static final String BOOK_AUTHOR = "The Defeated Adventurer";

        public static WrittenBookContent CONTENT = new WrittenBookContent(Filterable.passThrough(BOOK_TITLE), BOOK_AUTHOR, 3, List.of(
                Filterable.passThrough(
                        Component.literal(
                                """
                                        §l§6RuleMaster BOSS: Livo§r
                                        """
                        ).append(
                                createPageLinkWithoutHover("Lore\n", 2, true)
                        ).append(
                                createPageLinkWithoutHover("How to find\n", 6, true)
                        ).append(
                                createPageLinkWithoutHover("Basics\n", 9, true)
                        ).append(
                                createPageLinkWithoutHover("Good to Know\n", 11, true)
                        ).append(
                                Component.literal("Abilities\n• ")
                        ).append(
                                createPageLinkWithoutHover("Vitalia\n", 13, true)
                        ).append(
                                Component.literal("• ")
                        ).append(
                                createPageLinkWithoutHover("Livo\n\n", 19, true)
                        ).append(
                                Component.literal("Click on the texts to go to the pages!")
                        )
                ), // P1
                Filterable.passThrough(
                        Component.literal(
                                "§lLORE\n"
                        ).append(
                                Component.literal(
                                        """
                                        Scattered Blood Crystals are the source of all life for humanity and a few allied species. A select number of these beings carry a crystal within them, granting the sacred power to create new life — a power that ravages the body.
                                        """)
                        ).append(
                                createPageLinkWithoutHover("Back", 1, true)
                        )
                ), // P2
                Filterable.passThrough(
                        Component.literal(
                                """
                                To bear this burden for her children, the entity Livo was created. She gathered the collective energy of every Blood Crystal into herself, using the Totem of Reborn to forge humanity. As the crystals’ unstable power grew, she forged the Blood Crystal Staff to
                                """
                        )
                ), // P3
                Filterable.passThrough(
                        Component.literal(
                                """
                               contain it, but was ultimately consumed. Now, Livo unleashes that contained energy upon the world, targeting adventurers and crystal-bearers alike with devastating force.
                               
                               Generations of heroes have fallen before her, their efforts utterly futile.
                               """)
                ), // P4
                Filterable.passThrough(
                        Component.literal(
                                """
                               Her legend is now one of despair, and no new challengers dare rise to face the vessel of life’s own crushing power.
                               """)
                ), // P5
                Filterable.passThrough(
                        Component.literal(
                                "§lHOW TO FIND\n"
                        ).append(
                                Component.literal(
                                        """
                                        There are two ways to find Livo and her structure, the Cradle of Life.
                                        
                                        1. Find Livo using an Eye of Birth
                                        2. Trade with Cartographers to get a Blood-stained Map
                                        
                                        
                                        """
                                )
                        ).append(
                                createPageLinkWithoutHover("Back", 1, true)
                        )
                ), // P6
                Filterable.passThrough(
                        Component.literal(
                                """
                                1. The Eye of Birth
                                
                                An Eye of Birth can be crafted with an Eye of Ender, and surrounding the eye with 8 Blood Crystals
                                """)
                ), // P7
                Filterable.passThrough(
                        Component.literal(
                                """
                                2. Cartographer Maps
                                
                                Cartographers can sell maps that locate to the Cradles of Life.
                                
                                However, it is not recommended to do so due to where they are located (more on that\s""").append(
                                createPageLinkWithoutHover("here", 9, true)
                        ).append(").")
                ), // P8
                Filterable.passThrough(
                        Component.literal("§lBASICS\n")
                                .append(
                                        Component.literal(
                                                """
                                                Cradles of Life, a rare structure to be found, can be found in the End.
                                                
                                                Here resides Livo, and her miniboss Vitalia, both having rule-based mechanics.
                                                
                                                
                                                
                                                """)
                                ).append(
                                        createPageLinkWithoutHover("Back", 1, true)
                                )
                ), // P9
                Filterable.passThrough(
                        Component.literal(
                                """
                                Cycle-based Combat
                                
                                Both Livo and Vitalia has special abilities, where they are §linvulnerable§0 for some time.
                                At the cost of that, they will be more vulnerable to attacks at other times.
                                """
                        )
                ), // P10
                Filterable.passThrough(
                        Component.literal("§lGOOD TO KNOW\n")
                                .append(
                                        Component.literal(
                                                "§n§lLustfire\n"
                                        ).append(
                                                """
                                                Lustfire is an effect that limits any actions to one key, whether mouse or keyboard.
                                                
                                                For example,\s
                                                Sprint jumping is not allowed as Ctrl + Space + W is clicked.
                                                
                                                
                                                """
                                        )
                                ).append(
                                        createPageLinkWithoutHover("Back", 1, true)
                                )
                ), // P11
                Filterable.passThrough(
                        Component.literal(
                                """
                                Sneak walking is not allowed as Shift + a direction key is clicked.
                                
                                Attacking while standing still is allowed as only the Left Click button is clicked.
                                """
                        )
                ), // P12
                Filterable.passThrough(
                        Component.literal(
                                "§lABILITIES: VITALIA\n"
                        ).append(
                                Component.literal(
                                        "§n§lUppercut§r\n"
                                ).append(
                                        """
                                        Vitalia activates his crystal on his back, performs a powerful uppercut, rushing forward slightly and dealing X damage.
                                        
                                        This attack has a 25% chance to put every item on the target's hands on a 1 second
                                        """
                                ).append(
                                        createPageLinkWithoutHover("Back", 1, true)
                                )
                        )
                ), // P13
                Filterable.passThrough(
                        Component.literal(
                                """
                                cooldown.
                                
                                The target's movements, jumps, and other actions are not affected.
                                """)
                ), // P14
                Filterable.passThrough(
                        Component.literal("§n§lBlow Dust\n")
                                .append(
                                        """
                                        Vitalia blows dust from his mouth, dealing X damage every 0.5 seconds and applying the Lustfire effect for 5 seconds.
                                       
                                        Refer to the Lustfire effect\s"""
                                ).append(
                                        createPageLinkWithoutHover("here", 11, true)
                                ).append(Component.literal("."))
                ), // P15
                Filterable.passThrough(
                        Component.literal("§n§lCore Skill: Offense and Defense\n")
                                .append(
                                        """
                                        Every 8 seconds, Vitalia toggles the activeness of the crystal heart at the front of his body.
                                        
                                        A glowing crystal heart allows him to defend against most attacks, and gain a 70% damage reduction. During the defensive
                                        """
                                )
                ), // P16
                Filterable.passThrough(
                        Component.literal(
                                """
                                mode, Vitalia holds his arms in front of him.
                                
                                When Vitalia is attacked in his defensive mode, he stuns his targets for 2 seconds and reflects 30% of the damage done to him.
                                
                                The reflected damage is not decreased by the reduced damage
                                """)
                ), // P17
                Filterable.passThrough(Component.literal("from Vitalia's defensive mode.")
                ), // P18
                Filterable.passThrough(
                        Component.literal("§lABILITIES: LIVO\n")
                                .append("""
                                            This part is yet to be added
                                            I have way too much ideas that I need to organize them first :)
                                            
                                            
                                            
                                            
                                            
                                            
                                            
                                            """)
                                .append(
                                        createPageLinkWithoutHover("Back", 1, true)
                                )
                ) // P19
        ), true);;

    }
}
