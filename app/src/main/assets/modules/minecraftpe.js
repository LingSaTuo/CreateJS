var tag = ["Block","Entity","Item","Level","ModPE","Player","Renderer","Server",
                 "ArmorType","BlockFace","BlockRenderLayer","ChatColor","DimensionId",
                 "Enchantment","EnchantType","EntityRenderType","EntityType","ItemCategory",
                 "MobEffect","ParticleType","UseAnimation"]
var name = [["defineBlock","defineLiquidBlock","getAllBlockIds","getDestroyTime","getFriction","getRenderType","getTextureCoords","setColor","setDestroyTime",
             "setExplosionResistance","setFriction","setLightLevel","setLightOpacity","setRedstoneConsumer","setRenderLayer","setRenderType","setShape"],
             ["addEffect","getAll","getAnimalAge","getArmor","getArmorCustomName","getArmorDamage","getEntityTypeId","getExtraData","getHealth","getItemEntityCount",
              "getItemEntityData","getItemEntityId","getMaxHealth","getMobSkin","getNameTag","getPitch","getYaw","getRenderType","getRider","getRiding",
              "getTarget","getUniqueId","getVelX","getVelY","getVelZ","getX","getY","getZ","isSneaking","remove","removeAllEffects","removeEffect","rideAnimal","setAnimalAge",
               "setArmor","setArmorCustomName","setCape","setCarriedItem","setCollisionSize","setExtraData","setFireTicks","setHealth","setImmobile","setMaxHealth",
               "setMobSkin","setNameTag","setPosition","setPositionRelative","setRenderType","setRot","setSneaking","setTarget","setVelX","setVelY","setVelZ","spawnMob","setTarget"],
               ["addCraftRecipe","addFurnaceRecipe","addShapedRecipe","defineArmor","defineThrowable","getCustomThrowableRenderType","getMaxDamage","getMaxStackSize","getName",
               "getTextureCoords","getUseAnimation","internalNameToId","isValidItem","setCategory","setEnchantType","setHandEquipped","setMaxDamage","setProperties","setStackedByData","translatedNameToId"],
               ["addParticle","biomeIdToName","canSeeSky","destroyBlock","dropItem","executeCommand","explode","getAddress","getBiome","getBiomeName","getBrightness","getChestSlot",
               "getChestSlotCount","getChestSlotCustomName","getChestSlotData","getData","getDifficulty","getFurnaceSlot","getFurnaceSlotCount","getFurnaceSlotData","getGameMode",
               "getGrassColor","getLightningLevel","getRainLevel","getSignText","getTile","getTime","getWorldDir","getWorldName",
               "playSound","playSoundEnt","setChestSlot","setChestSlotCustomName","setDifficulty","setBlockExctraData","setFurnaceSlot","setGameMode","setGrassColor","setLightningLevel",
               "setNightMode","setRainLevel","setSignText","setSpawn","setSpawnerEntityType","setTile","setTime","spawnChicken","spawnCow","spawnMob"],
               ["dumpVtable","getBytesFromTexturePack","getI18n","getLanguage","getMinecraftVersion","getOS","langEdit","leaveGame","log","FoundFile",
               "readArray","openInputStreamFromTexturePack","overrideTexture","readData","removeData","resetFov","resetImages","saveData","selectLevel","setCamera",
               "setFoodItem","setFov","setGameSpeed","setGuiBlocks","setItem","setItems","setTerrain","setUiRenderDebug","showTipMessage","takeScreenshot"],
               ["addExp","addItemCreativeInv","addItemInventory","canFly","clearInventorySlot","enchant","getArmorSlot","getArmorSlotDamage",
               "getCarriedItem","getCarriedItemCount","getCarriedItemData","getDimension","getEnchantments","getEntity","getExhaustion","getExp","getHunger",
               "getInventorySlot","getInventorySlotCount","getInventorySlotData","getItemCustomName","getLevel","getName","getPointedBlockData",
               "getPointedBlockId","getPointedBlockSide","getPointedBlockX","getPointedBlockY","getPointedBlockZ","getPointedEntity","getPointedVecX","getPointedVecY","getPointedVecZ",
               "getSaturation","getScore","getSelectedSlotId","getX","getY","getZ","isFlying","isPlayer","setArmorSlot","setCanFly","setExhaustion","setExp","setFlying",
               "setHealth","setHunger","setInventorySlot","setItemCustomName","setLevel","setSelectedSlotId","setSaturation"],
               ["get","createHumanoidRenderer","renderer","model","modelPart"],
               ["getAddress","getAllPlayerNames","getAllPlayers","getPort","joinServer","sendChat"],
               ["boots","chestplate","helmet","leggings"],
               ["DOWN","EAST","NORTH","SOUTH","UP","WEST"],
               ["alpha","alpha_seasons","alpha_single_side","blend","doubleside","far","opaque","opaque_seasons","seasons_far","seasons_far_alpha","water"],
               ["AQUA","BEGIN","BLACK","BLUE","BOLD","DARK_AQUA","DARK_BLUE","DARK_GRAY","DARK_GREEN","DARK_PURPLE","DARK_RED","GOLD","GRAY","GREEN","RED","RESET","WHITE","YELLOW","LIGHT_PURPLE"],
               ["NETHER","NORMAL"],
               ["AQUA_AFFINITY","BANE_OF_ARTHROPODS","BLAST_PROTECTION","DEPTH_STRIDER","EFFICIENCY","FEATHER_FALLING","FIRE_ASPECT","FIRE_PROTECTION","FLAME","FORTUNE",
               "INFINITY","KNOCKBACK","LOOTING","LUCK_OF_THE_SEA","LURE","POWER","PROJECTILE_PROTECTION","PROTECTION","PUNCH","RESPIRATION","SHARPNESS","SILK_TOUCH","SMITE","THORNS",
               "UNBREAKING"],
               ["all","axe","book","bow","fishingRod","flintAndSteel","hoe","pickaxe","shears","shovel","weapon"],
               ["arrow","bat","blaze","boat","camera","chicken","cow","creeper","egg","enderman","expPotion","experienceOrb","fallingTile","fireball","fishHook",
               "ghast","human","ironGolem","item","lavaSlime","lightningBolt","map","minecart","mushroomCow","ocelot","painting","pig","player","rabbit","sheep",
               "silverfish","skeleton","slime","smallFireball","snowGolem","snowball","spider","squid","thrownPotion","tnt","unknownItem","villager","villagerZombie","witch","wolf",
               "zombie","zombiePigman"],
               ["ARROW","BAT","BLAZE","BOAT","CAVE_SPIDER","CHICKEN","COW","CREEPER","EGG","ENDERMAN","EXPERIENCE_ORB","EXPERIENCE_POTION","FALLING_BLOCK","FIREBALL","FISHING_HOOK","GHAST",
               "IRON_GOLEM","ITEM","LAVA_SLIME","LIGHTNING_BOLT","MINECART","MUSHROOM_COW","OCELOT","PAINTING","PIG","PIG_ZOMBIE","PLAYER","PRIMED_TNT","RABBIT","SHEEP",
               "SILVERFISH","SKELETON","SLIME","SMALL_FIREBALL","SNOWBALL","SNOW_GOLEM","SPIDER","SQUID","THROWN_POTION","VILLAGER","WOLF","ZOMBIE","ZOMBIE_VILLAGER"],
               ["DECORATION","FOOD","INTERNAL","MATERIAL","TOOL"],
               ["absorption","blindness","confusion","damageBoost","damageResistance","digSlowdown","digSpeed","effectIds","fireResistance","harm","heal","healthBoost","hunger",
               "invisibility","jump","movementSlowdown","movementSpeed","nightVision","poison","regeneration","saturation","waterBreathing","weakness","wither"],
               ["rainSplash","redstone","slime","smoke","smoke2","snowballpoof","spell","spell2","spell3","splash","suspendedTown","terrain","waterWake",
               "angryVillager","bubble","cloud","crit","dripLava","dripWater","enchantmenttable","fallingDust","flame","happyVillager","heart","hugeexplosion","hugeexplosionSeed",
               "ink","itemBreak","largeexplode","lava","mobFlame","mobFlame","note","portal"],
               ["bow","normal"]]
var functions = ["addItemInventory","bl_setMobSkin","bl_spawnMob","explode","getCarriedItem","getLevel","getPitch","getPlayerEnt","getPlayerX","getPlayerY",
                 "getPlayerZ","getTile","getYaw","preventDefault","rideAnimal","setNightMode","setPosition","setPositionRelative","setRot","setTile","setVelX","setVelY","setVelZ",
                 "spawnChicken","spawnCow","spawnPigZombie"]

           for(let a = 0 ; a < tag.length;a++){
            var names = name[a];
            for(let b = 0 ; b<names.length;b++){
                Autotip.add(tag[a],names[b],"");
            }
           }
           for(let a = 0 ; a<functions.length;a++){
                Autotip.add(functions[a],functions[a]+"(");
           }