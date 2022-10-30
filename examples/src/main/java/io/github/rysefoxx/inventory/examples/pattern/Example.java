/*
 * MIT License
 *
 * Copyright (c) 2022. Rysefoxx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package io.github.rysefoxx.examples.pattern;

import io.github.rysefoxx.content.IntelligentItem;
import io.github.rysefoxx.content.InventoryProvider;
import io.github.rysefoxx.pagination.InventoryContents;
import io.github.rysefoxx.pagination.InventoryManager;
import io.github.rysefoxx.pagination.RyseInventory;
import io.github.rysefoxx.pattern.ContentPattern;
import io.github.rysefoxx.pattern.SearchPattern;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * @author Rysefoxx | Rysefoxx#6772
 * @since 6/12/2022
 */
public class Example extends JavaPlugin {

    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        this.inventoryManager = new InventoryManager(this);
        this.inventoryManager.invoke();
    }

    private void contentPattern() {
        RyseInventory.builder()
                .manager(this.inventoryManager)
                .title("This is a ContentPattern inventory")
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        ContentPattern pattern = contents.contentPattern();
                        pattern.define(
                                "XXXXXXXXX",
                                "XOOOOOOOX",
                                "XXXXOXXXX",
                                "XXXXOXXXX",
                                "XXXXOXXXX",
                                "XXXXOXXXX");

                        pattern.set('O', IntelligentItem.empty(new ItemBuilder(Material.STONE).displayName("I am a stone with which nothing happens.").build()));
                        pattern.set('X', IntelligentItem.empty(new ItemBuilder(Material.BARRIER).displayName("I am a barrier with which nothing happens.").build()));
                    }
                })
                .build(this);
    }

    private void searchPattern() {
        RyseInventory.builder()
                .manager(this.inventoryManager)
                .title("This is a SearchPattern inventory")
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        contents.fill(new ItemStack(Material.CACTUS));

                        SearchPattern pattern = contents.searchPattern();
                        pattern.define(
                                "XXXXXXXXX",
                                "XOOOOOOOX",
                                "XXXXOXXXX",
                                "XXXXOXXXX",
                                "XXXXOXXXX",
                                "XXXXOXXXX");

                        List<IntelligentItem> intelligentItems = pattern.searchForIntelligentItems('O');
                        List<ItemStack> itemStacks = pattern.searchForItemStacks('X');

                        Bukkit.broadcastMessage("Hey! Based on the pattern, " + intelligentItems.size() + " IntelligentItem's could be found.");
                        Bukkit.broadcastMessage("Hey! Based on the pattern, " + itemStacks.size() + " itemStack's could be found.");
                    }
                })
                .build(this);
    }

}
