package com.ppshein.PlanetMyanmarDictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class common {
	static ArrayList<common> GetSearchResults(){
    	ArrayList<common> results = new ArrayList<common>();
    	
    	common item_details = new common();
    	item_details.setName("Home");
    	item_details.setImageNumber(1);
    	results.add(item_details);
    	
    	item_details = new common();
    	item_details.setName("Bookmarks");
    	item_details.setImageNumber(2);
    	results.add(item_details);
    	
    	item_details = new common();
    	item_details.setName("Sync");
    	item_details.setImageNumber(3);
    	results.add(item_details);
    	
    	item_details = new common();
    	item_details.setName("About");
    	item_details.setImageNumber(4);
    	results.add(item_details);
    	
    	return results;
    }
	
	public static String getMessage() {
		final String rank[]=  {
			"Face facts, you are a hilarious drunk.",
			"The cleaning lady is spitting in your sock drawer.",
			"The person in the stall before you had untreated genital warts. Good luck!",
			"The body under the couch is starting to smell.",
			"You will buy some minors alcohol today.",
			"Your appearance provides amusement to hundreds every day.",
			"Your next shower will be interrupted by a large mildly poisonous insect.",
			"The spider that bit you in your sleep was radioactive and you now have super powers. Go try them out!",
			"It's time to tell your family about your second head.",
			"Sell the porn before she finds it.",
			"The world is your oyster. Gross.",
			"Your current life-threatening condition was entirely preventable. Isn't that funny?",
			"A man with a beak will attempt to bill you.",
			"Your cable company hates you.",
			"Try to answer every phone call before the first ring finishes. Success will follow",
			"Your life is a series of ups and downs. Like a toilet plunger.",
			"There's no time like the present, so have that unsightly growth removed.",
			"It might be skin cancer or it might just be some leftover BBQ sauce.",
			"It's Time to dive into a steaming pile of YOU!",
			"It's time to leap into a jagged canyon of YOU!",
			"It's time to fall into a fetid pond of YOU!",
			"It's time to plunge into a rancid lake of YOU!",
			"It's time to hurl yourself into a rotting swamp of YOU!",
			"It's been years. Go ahead, expose yourself in public again.",
			"A fart plays a pivitol role in your relationship.",
			"You are not as stupid as your friends say you are.",
			"Your misgivings are grounded in paranoia.",
			"You will be eaten by coyotes.",
			"Don't order the miso.",
			"Tighen the caps on all your medications.",
			"Your pet humps your pillow when you're at work.",
			"Your mailman is sleeping with your mother.",
			"You have the athleticism of a mullosk.",
			"That smell IS you. Seriously.",
			"Bathing isn't overrated.",
			"That hot tip you got will make you look foolish.",
			"Sell!",
			"Give up on your dreams, everyone else has.",
			"A drinking problem would make you more interesting.",
			"Do it!",
			"Don't do it!",
			"The priest is lying about what happened.",
			"Your parents wanted a boy.",
			"Your father is not an astronaut.",
			"Chewing gum will save your life this week.",
			"Your seats for the show will be abysmal.",
			"Think twice.",
			"Don't think, just do.",
			"Yeah, she was talking about you.",
			"The receptionist is reading your mail.",
			"Don't go to the company party.",
			"Duck!",
			"Less TV, more porn.",
			"Your best friend just called you a loser.",
			"Your boss is plotting against you. You have to stop him. Now!",
			"Take a lower paying position.",
			"Your refrigerator is filthy.",
			"Give up on your dream of being a master debater.",
			"Being unique just means you don't fit in.",
			"There's nothing good on tonight.",
			"Put yourself out there.",
			"Keep all the receipts, even the embarassing ones.",
			"Don't worry, you can get to the mailbox before they find out.",
			"The only thing to fear is fear itself.",
			"Yep, that's a panda.",
			"Go with the waffles instead of the pancakes.",
			"You won't have enough to cover your tab. Wear running shoes.",
			"A fart will follow you into the car, ruining your date.",
			"Look over your your tax forms again. Boy are you gonna shit.",
			"Your curtains are not opaque.",
			"Check your voicemail.",
			"Your neighbor harbors a deep resentment.",
			"Take the long way home. In fact, just give home a miss today.",
			"You will come into a nice automobile. Make sure to clean up before the owner finds out.",
			"Yep, it's contagious.",
			"Truth may be perception, but you're just a freaking liar.",
			"Time heals all rug burns.",
			"The bad news is that you're actually related.",
			"You were adopted.",
			"Tread lightly.",
			"You will be the butt of a hilarious joke.",
			"Against all odds, you will accomplish a basic task.",
			"You will reach new heights of banality today.",
			"You will find something you lost and lose it again a short time later.",
			"You will surrender to mediocrity.",
			"You will wish you had stayed in bed today.",
			"You will suddenly realize you're naked on public transportation.",
			"You will get caught surfing job sites at work.",
			"You will receive a job offer from a prostitute.",
			"You will get lost while driving.",
			"You will suddenly understand both particle physics and empathy.",
			"You will step in something in a few minutes.",
			"Your dandruff will increase exponentially and then suddenly stop.",
			"You will meet a six-foot midget.",
			"It's clear your future lies in the church. But they won't have you.",
			"Tell that person you love them. You know who we're talking about. Wait! No, not him! Stop you fool!",
			"Your coworkers are planning your birthday party. But they have the wrong date.",
			"Today will be full of hits, misses, and gun jams.",
			"The milk is going bad as you read this.",
			"Stick to tea. Coffee is making the tumor bigger.",
			"You can accomplish anything, as long as it's painfully easy.",
			"You will get a phone call that will change your life as it relates to pizza.",
			"You will receive a phone call with an incredible offer about long distance. Take it!",
			"You will receive a phone call from a Norwegian.",
			"You will receive a phone call from yourself in the future.",
			"You will receive a phone call tonight. Wait for six rings before answering.",
			"Your answering machine is full.",
			"Your car has a flat.",
			"Pigeons are living in your wheel wells.",
			"Replace your tires after 10,000 miles or two years, whichever comes first.",
			"Your car will begin pulling slightly to the center.",
			"Three words: Learn how to count.",
			"Throw the baby out with the bathwater.",
			"You will contract an illness that's more irritating than dangerous.",
			"You'll discover your eyes are different sizes.",
			"Big success and crushing failure this week!",
			"Ride the rollercoaster. Surf the wave. Basically, give up.",
			"Eat more fruit.",
			"Stop stressing over your unfortunate looks.",
			"Today will bring you great joy. Feel free to run in place like a child.",
			"Face facts, you have a dreadful singing voice.",
			"Learn how to drive jerk!",
			"There's a squirrel in a nearby tree thinking about crapping in your hair.",
			"The bats in your attic are organizing.",
			"There is someone living in your walls. And he's pissed.",
			"The bump on your back is actually the head of your unformed twin.",
			"There's a video of you on YouTube that you should probably investigate.",
			"Two words: Head Lice",
			"Two words: Blunt Trauma",
			"Two words: Evil Dwarf",
			"Two words: Alien Contact",
			"Watch more TV and pay less attention to your family.",
			"Get Religion. Quick!",
			"Your workouts would be more effective if you stopped eating between sets.",
			"Hide from those who mean to tickle you.",
			"You aren't as attractive as your driver's license indicates.",
			"Your weight problem is not why people don't like you.",
			"People like you make me sick.",
			"The stars say shut up.",
			"Quit whining about the pain, it only makes it worse for everyone else.",
			"Take that trip sooner rather than later.",
			"Get out of the way.",
			"Wash your hands more often.",
			"You will fart in front of a large group tomorrow.",
			"Your zipper is down.",
			"Your clothes all need to be replaced.",
			"Things are about to get interesting, but not for you.",
			"You're not the only one who thinks you're inadequate.",
			"You have a gay sibling. Shhhh.",
			"Believe everything you read.",
			"It's OK, it happens to everyone. Just not as often.",
			"Your tires were just slashed.",
			"Your dinner will make you pray for death.",
			"Today is what life's all about. Go get 'em tiger!",
			"You are so dead.",
			"Hide it, no one saw!",
			"Money isn't everything.",
			"You win some, you lose some.",
			"The hardest part is ahead. Stay home and rest.",
			"Call in sick. You'll know why.",
			"It's probably just a big freckle. No worries.",
			"Seriously? You're wearing that?",
			"Stay away from the parrot.",
			"A kangaroo will play a pivotal role in the weekend.",
			"Sure, you're tough. Yikes!",
			"You have something in your teeth.",
			"Wipe the seat, pig.",
			"You will lose at pool this weekend.",
			"Someone will spit in your taco.",
			"You will contract a previously unheard of strain of the flu.",
			"You will scratch yourself at the absolute wrong time.",
			"Your paranoia is justified.",
			"You will be mistaken for a celebrity you detest.",
			"Quit your job. Who needs that crap?",
			"The Feds want to talk about your surfing habits.",
			"You will find yourself nervous and gassy this evening.",
			"You will come up with a great name for your genitals.",
			"Spend all your money on the lottery. This time it's gonna happen.",
			"Your poker face needs work.",
			"Try a new deodorant.",
			"Your socks don't match.",
			"Have you changed your diet recently? It smells like it.",
			"Today is the last day of the rest of your life.",
			"Money is your friend today. Borrow heavily and play the lottery.",
			"You will have a chance encounter with romance today so take a bath for god's sake.",
			"A stranger may mean you harm this week so greet everyone with a blood-curdling shriek.",
			"Your aura is dim. Have it checked.",
			"You will be asked to dance by a stranger wearing a sandwich board.",
			"Your sudden sweet tooth is probably a sign of impending doom.",
			"Your carpet will stop matching your curtains.",
			"Keep an eye out for rabid squirrels this month.",
			"You will join a club and be elected president. Submit immediately to graft and corruption.",
			"Your missing sock is in a parallel dimension.",
			"You will find a turtle in your laundry.",
			"You're no athlete. Stop kidding yourself.",
			"You miss the toilet more than you think.",
			"Your bones are slowly turning to dust. Won't be long now.",
			"Your bank is stealing from you.",
			"You're not a cannibal, you're just curious.",
			"The termites in your skull are making more noise than ever this week.",
			"Know that you're in trouble when your significant other refers to your manhood as a *skin tag*.",
			"While you were away from home, a stranger showered in your bathroom.",
			"Every time you lay your head on the pillow, a luminous red dot appears on your forehead.",
			"It's okay to isolate, as long as you don't do it alone.",
			"A large windfall is coming to you. Only it's meteorological and not financial.",
			"Do us a favor, get the surgery. Please.",
			"To repeat, a prolapsed anus is NOT normal.",
			"Your organs will be harvested and marketed as souvenirs to the Xeaibobia Tribe of New Guinea.",
			"That adorable sun freckle on your nose will leave you one year later on a ventilator while your dermatologist lectures you about SPF.",
			"Tired of the constant accusations, you will buy a dog to take the heat for farting.",
			"Spend less time worrying about appearances and more time worrying about how people see you.",
			"You were visited by aliens last night and, based on you, they found our species not worth investigating further.",
			"You will become illiterate as soon as you finish this sentence.",
			"Your smile scares children.",
			"Your new sex move is not pleasurable for anyone. Just stop it.",
			"The police are listening.",
			"Your favorite aunt is actually your favorite uncle.",
			"Baked goods are in abundance.",
			"They know about the hamsters.",
			"You need a pet. Go ask someone to pet you.",
			"Hug your pharmacist.",
			"Walk it off.",
			"Stop touching yourself.",
			"It's not a tumor.",
			"Breathe less.",
			"Work out. Then again, why bother?",
			"All is not lost. But most of it is.",
			"You'll find it in the last place you look for it.",
			"Your heroes are all cowards.",
			"They wouldn't have said it if they didn't mean it.",
			"Do something for yourself for once. Do it! What did I just say?",
			"Go ahead and hide from the world. Not that the world will notice.",
			"Good things come in ones.",
			"Good things come in tiny packages.",
			"It's as bad as you think.",
			"Your next Career: Mime",
			"Your next Career: Chimney Sweep",
			"Your next Career: Foxy Boxer",
			"Your next Career: Ventriloquist",
			"Your next Career: Powerless Superhero",
			"Your next Career: Veterinarian",
			"Your next Career: Horoscope Writer",
			"Your next Career: Suicide Hotline Operator",
			"Your next Career: Sears Photographer",
			"Your next Career: Skeptic",
			"Your next Career: Seat Filler",
			"Your next Career: Mortician",
			"Your next Career: Drunk",
			"Your next Career: Pet Therapist",
			"Your next Career: Sketch Artist",
			"Your next Career: Train Conductor",
			"Your next Career: Unknown Graphic Novelist",
			"Your next Career: Square Dance Caller",
			"Your next Career: Serial Temp",
			"Your next Career: Ferret Groomer",
			"Your next Career: Park Security",
			"Your next Career: Wal-Mart Greeter",
			"Your next Career: Pubic Stylist",
			"Your next Career: Salad Bar Security",
			"Your next Career: Roadkill Cleanup Enforcement",
			"Your next Career: Taxidermist",
			"Your next career: Gangster Florist",
			"Your next career: Master Level Ferris Wheel Operator",
			"Next Career: Satanic Guidance Counselor",
			"Your next career: Spit Polisher",
			"Your Next Career: Gay Prison Guard",
			"Your next career: Sad Clown Painter.",
			"The transit of the sun into Cancer is cryptic.",
			"Friendships and social circles will turn on you in the coming days.",
			"Your fun side comes out in obscene ways.",
			"You’ll be more inclined to relax almost to the point of coma.",
			"Tomorrow brings more soup.",
			"Keep a close eye on your bank balance today, which shouldn't be hard given that you're nearly broke.",
			"You need to spend some time thinking about your future.",
			"Romance is very definitely in the air...wait, that's doughnuts.",
			"Someone who you least expect to be interested...isn't.",
			"Give encouragement to the hopeless dregs that idolize you.",
			"The planetary influences will irritate your bowels this week.",
			"Your exciting plans for the weekend bore the planets.",
			"Keep your mood swings under control.",
			"Venus is making itself felt in your pants today.",
			"Your usually dominant manner is replaced by a coy submissiveness today. Go with it.",
			"You find your new feelings rather confusing. Speak to the police.",
			"You have a stepsister living in Mobile, Alabama. She's coming over for dinner tonight.",
			"You've always been committed to being yourself. Stop it.",
			"Why be yourself when you can be someone more interesting?",
			"Your every fleeting thought is a pearl.",
			"No one makes eating noises quite like you.",
			"Failure is certainly an option.",
			"Run! For God's sake, RUN!",
			"There's no substitute for unbridled laziness.",
			"That wasn't his finger.",
			"Your gas problem is not going unnoticed.",
			"There are bodies in your trunk.",
			"Don't leave the house for at least a month.",
			"You are being watched but they're really bored.",
			"Take a chance on that hottie down in the mailroom with the odd limp.",
			"It will rain tomorrow. Guaranteed.",
			"A windfall of lunchmeat awaits.",
			"There's no one else like you, thank God.",
			"What you see as a talent, others see as a sign of weakness.",
			"Stop being late all the time.",
			"You're no fun. No fun at all.",
			"Your best friend is sleeping with your pet.",
			"That favorite coworker of yours secretly hates you.",
			"Sleep on the floor.",
			"Stay away from dairy products.",
			"Your dementia finally gets you somewhere.",
			"Just go sleep on the couch tonight. You don't want to know why.",
			"Buy a cat and name it Chris. It will get you out of a jam.",
			"Clean towels will be key.",
			"Hide the porn.",
			"Clear your browser history.",
			"Don't worry, it doesn't make you gay.",
			"The goat will become increasingly important.",
			"That outfit makes you look fat, OK? Get over it.",
			"You have a great singing voice, stop hoarding it!",
			"You thought everyone forgot about it, but they didn't.",
			"Yeah, you're probably gay.",
			"Name your sexual organs after old time TV characters.",
			"Your keys are missing.",
			"Stop smoking. Or start. Whichever you're not doing.",
			"Kill two birds with one stone.",
			"Eat out more, but eat less when you're out.",
			"Eat in more and eat more when you eat.",
			"Don't wear sunscreen this month.",
			"Something will slip out of your hand and break your foot tomorrow.",
			"Bring extra Kleenex.",
			"You'll throw out your back eating a taco this week.",
			"The sun is shining up your ass. Enjoy it.",
			"Clean the gutters, so to speak.",
			"You will disover a new and apalling skill.",
			"Shave your head.",
			"Adopt a chicken.",
			"Defrost your freezer before it's too late.",
			"Buy a box of fake eyeballs. You'll thanks us later.",
			"Repent!",
			"You will get a worthless coupon in the mail.",
			"Don't sleep, shriners want to eat you.",
			"Your lucky number is pi.",
			"Your lucky number is -6.",
			"Your lucky number is imaginary.",
			"Your lucky number is 0.",
			"Your lucky number is .437.",
			"Your lucky number is 11.",
			"Your lucky number is 37.",
			"Your lucky number is a bakers dozen.",
			"You will be taken in by the glamour and glitz of online poker and develop carpal tunnel syndrome.",
			"You will discuss a matter of grave importance with someone you mistake for a coworker.",
			"You will develop an attractive skin condition.",
			"You will opt for plastic surgery this year. Label the right part before you go under or they'll have no clue where to start.",
			"All your furniture has moved one inch to the left.",
			"Your hair is plotting against you again.",
			"God will tell you the meaning of your existence, but in a thick, unintelligible accent.",
			"You're not the only one depressed by your failures.",
			"Succeeding at sucking is not really success.",
			"You'll get 'The Devil Went Down to Georgia' stuck in your head for the next week.",
			"Aaaaah! There's a bug on your shoulder!",
			"Hold out for a better deal from your boss when he offers to fire you.",
			"You will fall asleep in a meeting and snore like a leaf blower.",
			"A sneeze will nearly cripple you tomorrow.",
			"Your enemies are massing at the borders.",
			"Nothing tastes as sweet as winning. Except for winning at the expense of others. Remember that.",
			"You will inherit debt this month.",
			"An orphaned hillbilly boy will begin living in your fireplace.",
			"You will inadvertantly shoplift several times this week.",
			"Your prison record WILL come up. No point hiding it anymore.",
			"An argument over a parking ticket will lead to romantic entanglement.",
			"Your favorite cousin wants to sleep with you.",
			"You can do anything, but not everything. <br> - David Allen",
			"Perfection is achieved, not when there is nothing more to add, but when there is nothing left to take away. <br> - Antoine de Saint-Exup�ry",
			"The richest man is not he who has the most, but he who needs the least. <br> - Unknown Author",
			"You miss 100 percent of the shots you never take. <br> - Wayne Gretzky",
			"Courage is not the absence of fear, but rather the judgement that something else is more important than fear. <br> - Ambrose Redmoon",
			"You must be the change you wish to see in the world. <br> - Gandhi",
			"When hungry, eat your rice; when tired, close your eyes. Fools may laugh at me, but wise men will know what I mean. <br> - Lin-Chi",
			"The third-rate mind is only happy when it is thinking with the majority. The second-rate mind is only happy when it is thinking with the minority. The first-rate mind is only happy when it is thinking. <br> - A. A. Milne",
			"To the man who only has a hammer, everything he encounters begins to look like a nail. <br> - Abraham Maslow",
			"We are what we repeatedly do; excellence, then, is not an act but a habit. <br> - Aristotle",
			"A wise man gets more use from his enemies than a fool from his friends. <br> - Baltasar Gracian",
			"Do not seek to follow in the footsteps of the men of old; seek what they sought. <br> - Basho",
			"Everyone is a genius at least once a year. The real geniuses simply have their bright ideas closer together. <br> - Georg Christoph Lichtenberg",
			"What we think, or what we know, or what we believe is, in the end, of little consequence. The only consequence is what we do.	<br> - John Ruskin",
			"The real voyage of discovery consists not in seeking new lands but seeing with new eyes.	<br> - Marcel Proust",
			"Work like you don�t need money, love like you�ve never been hurt, and dance like no one�s watching	<br> - Unknown Author",
			"Try a thing you haven�t done three times. Once, to get over the fear of doing it. Twice, to learn how to do it. And a third time, to figure out whether you like it or not. <br> - Virgil Garnett Thomson",
			"Even if you�re on the right track, you�ll get run over if you just sit there. <br> - Will Rogers",
			"People often say that motivation doesn�t last. Well, neither does bathing � that�s why we recommend it daily. <br> - Zig Ziglar",
			"Before I got married I had six theories about bringing up children; now I have six children and no theories. <br> - John Wilmot",
			"What the world needs is more geniuses with humility, there are so few of us left. <br> - Oscar Levant",
			"Always forgive your enemies; nothing annoys them so much. <br> - Oscar Wilde",
			"I�ve gone into hundreds of [fortune-teller's parlors], and have been told thousands of things, but nobody ever told me I was a policewoman getting ready to arrest her. <br> - New York City detective",
			"When you go into court you are putting your fate into the hands of twelve people who weren�t smart enough to get out of jury duty. <br> - Norm Crosby",
			"Those who believe in telekinetics, raise my hand. <br> - Kurt Vonnegut",
			"Just the fact that some geniuses were laughed at does not imply that all who are laughed at are geniuses. They laughed at Columbus, they laughed at Fulton, they laughed at the Wright brothers. But they also laughed at Bozo the Clown. <br> - Carl Sagan",
			"My pessimism extends to the point of even suspecting the sincerity of the pessimists. <br> - Jean Rostand",
			"Sometimes I worry about being a success in a mediocre world. <br> - Lily Tomlin",
			"I quit therapy because my analyst was trying to help me behind my back. <br> - Richard Lewis",
			"We�ve heard that a million monkeys at a million keyboards could produce the complete works of Shakespeare; now, thanks to the Internet, we know that is not true. <br> - Robert Wilensky",
			"If there are no stupid questions, then what kind of questions do stupid people ask? Do they get smart just in time to ask questions? <br> - Scott Adams",
			"If the lessons of history teach us anything it is that nobody learns the lessons that history teaches us. <br> - Anon",
			"When I was a boy I was told that anybody could become President. Now I�m beginning to believe it. <br> - Clarence Darrow",
			"Laughing at our mistakes can lengthen our own life. Laughing at someone else�s can shorten it. <br> - Cullen Hightower",
			"There are many who dare not kill themselves for fear of what the neighbors will say. <br> - Cyril Connolly",
			"There�s so much comedy on television. Does that cause comedy in the streets? <br> - Dick Cavett",
			"All men are frauds. The only difference between them is that some admit it. I myself deny it. <br> - H. L. Mencken",
			"I don�t mind what Congress does, as long as they don�t do it in the streets and frighten the horses. <br> - Victor Hugo",
			"I took a speed reading course and read �War and Peace� in twenty minutes. It involves Russia. <br> - Woody Allen",
			"The person who reads too much and uses his brain too little will fall into lazy habits of thinking. <br> - Albert Einstein",
			"Believe those who are seeking the truth. Doubt those who find it. <br> - Andr� Gide",
			"It is the mark of an educated mind to be able to entertain a thought without accepting it. <br> - Aristotle",
			"I�d rather live with a good question than a bad answer. <br> - Aryeh Frimer",
			"We learn something every day, and lots of times it�s that what we learned the day before was wrong. <br> - Bill Vaughan",
			"I have made this letter longer than usual because I lack the time to make it shorter. <br> - Blaise Pascal",
			"Don�t ever wrestle with a pig. You�ll both get dirty, but the pig will enjoy it. <br> - Cale Yarborough",
			"An inventor is simply a fellow who doesn�t take his education too seriously. <br> - Charles F. Kettering",
			"Asking a working writer what he thinks about critics is like asking a lamppost how it feels about dogs. <br> - Christopher Hampton",
			"Better to write for yourself and have no public, than to write for the public and have no self. <br> - Cyril Connolly",
			"Never be afraid to laugh at yourself, after all, you could be missing out on the joke of the century. <br> - Dame Edna Everage",
			"I am patient with stupidity but not with those who are proud of it. <br> - Edith Sitwell",
			"Normal is getting dressed in clothes that you buy for work and driving through traffic in a car that you are still paying for � in order to get to the job you need to pay for the clothes and the car, and the house you leave vacant all day so you can afford to live in it. <br> - Ellen Goodman",
			"The cure for boredom is curiosity. There is no cure for curiosity. <br> - Ellen Parr",
			"Advice is what we ask for when we already know the answer but wish we didn�t. <br> - Erica Jong",
			"Some people like my advice so much that they frame it upon the wall instead of using it. <br> - Gordon R. Dickson",
			"The trouble with the rat race is that even if you win, you�re still a rat. <br> - Lily Tomlin",
			"Never ascribe to malice, that which can be explained by incompetence. <br> - Napoleon (Hanlon�s Razor)",
			"Imagination was given to man to compensate him for what he is not, and a sense of humor was provided to console him for what he is. <br> - Oscar Wilde",
			"When a person can no longer laugh at himself, it is time for others to laugh at him. <br> - Thomas Szasz",
		};
		
	    Random randInt = new Random();
	    String b = rank[randInt.nextInt(rank.length)];	    
	    return b;
	}
	
	
    public static void addBookmark (String notedword, Context context) {    	
	 try {
		 
		 DatabaseUtil dbUtil = new DatabaseUtil(context);
		 
	     dbUtil.open();        
	     Cursor cursor = dbUtil.fetchBookmark(notedword);
	     String getReturn = cursor.getString(1);
	     cursor.close();
	     dbUtil.close();
	     Log.v("Existing Bookmarks", getReturn);
	  } catch (Exception e){
		DatabaseUtil dbUtil = new DatabaseUtil(context);
		dbUtil.open();
		dbUtil.insertBookmark(notedword);
		dbUtil.close();		
		Toast toast = Toast.makeText(context, "Successfully bookmarked", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	  } 
    }
    
    public static String ProcessBookmark(String notedword, Context context) {
    	String returnString = "nofade";
		try {
			
			DatabaseUtil dbUtil = new DatabaseUtil(context);
			
			dbUtil.open();        
			Cursor cursor = dbUtil.fetchBookmark(notedword);
			String getBookmark = cursor.getString(1);
			cursor.close();
			dbUtil.close();
			if (getBookmark.toString() != "") {
				dbUtil.open();        
				boolean IsDeleted = dbUtil.deleteBookmark(notedword);
				Log.v ("Bookmarks", "Bookmark is deleted " + IsDeleted);
				dbUtil.close();
				returnString = "nofade";				
			}
		} catch (Exception e){
			addBookmark(notedword, context);
			returnString = "fade";
		}
		return returnString;
    }
    
    public static String checkBookmark(String notedword, Context context) {
		String returnString = "nofade";
		try {
			
			DatabaseUtil dbUtil = new DatabaseUtil(context);
			
			dbUtil.open();        
			Cursor cursor = dbUtil.fetchBookmark(notedword);
			String getBookmark = cursor.getString(1);
			cursor.close();
			dbUtil.close();
			if (getBookmark.toString() != "") {
				returnString = "fade";
			}
		} catch (Exception e){
			returnString = "nofade";
		}
		
		return returnString;
    }
    
	
	public static Boolean isNetAvailable(Context con)  {		  
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
                return true;
            }
        }
        catch(Exception e){
           e.printStackTrace();
        }
        return false;
    }    
    
    public static JSONObject getJSONfromURL(String url, String param1){
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		
		//http post
	    try{
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(url);
	            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	        	pairs.add(new BasicNameValuePair("assignid", param1));	        	
	        	httppost.setEntity(new UrlEncodedFormEntity(pairs));	            
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();

	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	    }
	    
	  //convert response to string
	    try{
	      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf8"),8);
	      StringBuilder sb = new StringBuilder();
	      String line = null;
	      while ((line = reader.readLine()) != null) {
	    	  sb.append(line + "\n");
	      }
	      is.close();
	      result = sb.toString();
	      result = result.replace("<META NAME=\"ColdFusionMXEdition\" CONTENT=\"ColdFusion DevNet Edition - Not for Production Use.\">", "");
	    }
	    catch(Exception e){
	    	Log.e("log_tag", "Error converting result " + e.toString());
	    }
	    
	    try{	    	
            jArray = new JSONObject(result);            
	    }catch(JSONException e){
	            Log.e("log_tag", "Error parsing data " + e.toString());
	    }
    
	    return jArray;
	}
    
	public static String getObj(String url, String param1){
		InputStream is = null;
		String result = "";
		
	    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        	pairs.add(new BasicNameValuePair("assignid", param1));
        	httppost.setEntity(new UrlEncodedFormEntity(pairs));	            
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

	    }catch(Exception e){
	        Log.e("log_tag", "Error in http connection "+e.toString());
	    }
        

	    try{
	      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf8"),8);
	      StringBuilder sb = new StringBuilder();
	      String line = null;
	      while ((line = reader.readLine()) != null) {
	    	  sb.append(line + "\n");
	      }
	      is.close();
	      result = sb.toString();
	    }
	    catch(Exception e){
	    	Log.e("log_tag", "Error converting result " + e.toString());
	    }
	    
	    return result;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}
	
	private String name ;
	private int imageNumber;	
 
	public static boolean checkDBExist(String FILENAME) {    	
    	File dbtest = new File(FILENAME);
    	if (dbtest.exists())
    		return true;
    	else
    		return false;
    }	
	
}
