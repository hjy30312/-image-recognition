
public enum Key {
    //键
    Esc("Esc",0),F1("F1",1),F2("F2",2),F3("F3",3),
    F4("F4",4),F5("F5",5),F6("F6",6),F7("F7",7),
    F8("F8",8),F9("F9",9),F10("F10",10),F11("F11",11),
    F12("F12",12),PrtSc("PrtSc",13),Scrlk("Scrlk",14),Pause("Pause",15),
    Tilde("~",16),Bang("!",17),At("@",18),Sharp("#",19),
    Dollar("$",20),Mod("%",21),Caret("^",22), And("&",23),
    Star("*",24),Lparentheses("(",25),Rparentheses(")",26),Underline("_",27),
    Equal("=",28),Backspace("Backspace",29),Ins("insert",30),Home("Home",31),
    PgUp("Pgup",32),Num("Num",33),Slash("/",34),Dash("-",35),Tab("Tab",36),
    Q("Q",37),W("W",38),E("E",39),R("R",40),T("T",41),Y("Y",42),
    U("U",43),I("I",44),O("O",45),P("P",46),Lbraces("{",47),
    Rbraces("}",48),Backslash("\\",49),Del("Delete",50),End("End",51),
    PgDn("PgDn",52),Seven("7",53),Eight("8",54),Nine("9",55),Plus("+",56),
    Caps("Caps",57),A("A",58),S("S",59),D("D",60),F("F",61),
    G("G",62),H("H",63),J("J",64),K("K",65),L("L",66),
    Colon(":",67),Quote("\"\"",68),Enter("Enter",69),Four("4",70),
    Five("5",71),Six("6",72),Shift("Shift",73),Z("Z",74),X("X",75),
    C("C",76),V("V",77),B("B",78),N("N",79),M("M",80),
    Comma(",",81),Point(".",82),Question("?",83),Up("↑",84),Down("↓",85),
    Left("←",86),Right("→",87),One("1",88),Two("2",89),Three("3",90),
    Ctrl("Ctrl",91),Win("Win",92),Alt("Alt",93),Space(" ",94),Fn("Fn",95),
    List("List",96),Zero("0",97);
    //序列值属性
    private int index;
    private String symbol;
    //构造器
    private Key(String symbol,int index){
        this.index = index;
        this.symbol = symbol;
    }
    //根据序列值获取对应键值
    public static Key getKey(double index){
        for(Key k:Key.values()){
            if(k.getIndex() == index){
                return k;
            }
        }
        return A;
    }
    //getter setter
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}