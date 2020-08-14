import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
class Process{
    String name;          //��������
    int allocation[];    //�ѷ������Դ��
    int MaxNeed[];       //�����������
    int needs[];         //��Ȼ��Ҫ
    boolean finshined=false;  //״̬

    @Override   //��д������toString����
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", allocation=" + Arrays.toString(allocation) +
                //", MaxNeed=" + Arrays.toString(MaxNeed) + ��ѡ��������������
                ", needs=" + Arrays.toString(needs) +
                ", finshined=" + finshined +
                '}';//��дtostring�������������������Ϣ
    }
}
public class Bank {

    private static  int KINDS=0;       //ϵͳ��Դ����ĸ���
    private static  int[] resource;   //������Դ������
    private static  int ProcessCount;  //��������
    private static  List<Process> team;//����һ��װ�������Ա������
    private static  int[] avaliable;  //ϵͳ��ǰ�ɷ�����Դ

    public static void InitAllResource(){          //��ʼ������Դ���ͽ�������
        System.out.println("��������Դ��������:");
        Scanner sc=new Scanner(System.in);
        KINDS=sc.nextInt();
        resource=new int[KINDS]; //��Դ������

        //����Դ����
        for(int i=0;i<resource.length;i++){
            System.out.println("�������"+i+"������Դ��");
            resource[i]=sc.nextInt();
        }

    }
    public static void InitProcess(){//��ʼ������
        if(KINDS==0){
            System.out.println("���ʼ������Դ��");
            return;
        }
        Scanner scanner=new Scanner(System.in);
        System.out.println("��������̵ĸ���");
        ProcessCount=scanner.nextInt();   //��ʼ�����̸���
        team=new ArrayList<>();

        for(int i=0;i<ProcessCount;i++){
            Process newProcess=new Process();//ÿ�δ���һ���µĽ��̶��󣬲������ʼ��
											//���������뵽˳�����
            System.out.println("��������̵����֣�");
            newProcess.name=scanner.next();
            String name=newProcess.name;
            newProcess.MaxNeed=new int[KINDS];
            for(int j=0;j<KINDS;j++){
                System.out.println("������"+name+"���̶�"+j+"����Դ�����������");
                newProcess.MaxNeed[j]=scanner.nextInt();
            }
            newProcess.allocation=new int[KINDS];
            for(int j=0;j<KINDS;j++){
                System.out.println("������"+name+"���̶�"+j+"����Դ�ѷ�������");
                newProcess.allocation[j]=scanner.nextInt();
            }
            team.add(newProcess);//����˳���
        }
        for(int i=0;i<team.size();i++){
            team.get(i).needs=new int[KINDS];
            for(int j=0;j<KINDS;j++){
                team.get(i).needs[j]=team.get(i).MaxNeed[j]-team.get(i).allocation[j];
            }
        }//����������ѷ����������󣬳�ʼ���������̵�����

    }
    public static void setAvaliable(){
        avaliable=new int[KINDS];
        for(int i=0;i<KINDS;i++){
            for(int j=0;j<team.size();j++) {
               avaliable[i] +=team.get(j). 
            }
        }
        for (int i=0;i<avaliable.length;i++){
            avaliable[i]=resource[i]-avaliable[i];
        }
    }
    public static boolean SafeCheck(){
        if(team == null){
            System.out.println("���ʼ��������Դ��Ϣ!");
            return false;
        }


        for(int i=0;i<KINDS;i++){//��ʼ�������
            if(avaliable[i]<0){
                System.out.println("��ǰ״̬���������³�ʼ����");
                System.out.println(Arrays.toString(avaliable));
                team=null;
                return false;
            }
        }

        String[] safeteam=new String[ProcessCount];//��Ű�ȫ����
        int safecount=0;//��¼��ȫ���е�����


        int work[]=new int[KINDS];
        for(int i=0;i<KINDS;i++){
            work[i]=avaliable[i];  //�ѵ�ǰ��avaliable�����ֵ���õ�work�����Է���
        }

        int index=0;//ѭ���ҽ���˳�����±�
        boolean choose=false;//ѡ����,����ǰ״̬�Ƿ��ܷ���
        int tmp=0;//����ֵ�Ƿ�ﵽ�˽��̳��ȣ���˵����ѯһȦ��

        while (safecount < team.size() && tmp <team.size()){
            //����ȫ������Ч��С�ڽ����� ���� ��ѯС��һȦ
             if(index >=team.size()){
                index=0;   //��ֹԽ�磬ѭ������
            }
                if(!team.get(index).finshined){//�жϵ�ǰ״̬
                    for(int i=0;i<KINDS;i++){//ѭ���ȽϿ��úͽ�������,������ѡ����Ϊtrue
                        if(team.get(index).needs[i]>work[i]){
                            choose=false;
                            tmp++;
                            index++;
                            break;
                        }else {
                            choose=true;
                        }
                    }
                    if(choose){   //�ҵ��ܷ���ģ��޸�work���飬��ʱ�޸�״ֵ̬
                        for (int j=0;j<KINDS;j++){
                            work[j]=work[j]+team.get(index).allocation[j];
                        }
                        team.get(index).finshined=true;
                        safeteam[safecount]=team.get(index).name;
                        safecount++;
                        index++;
                        tmp=0;//���¼���
                    }
                }else {
                    tmp++;//��ǰ�����Ѳ��ң����Ӳ��Ҵ���
                    index++;//�����±�ֵ
                }
        }
        for(int i=0;i<safeteam.length;i++){
            if(safeteam[i] == null){//��ȫ������һ��Ϊ�յĻ���˵������ȫ���������������Ϣ
                System.out.println("��ǰ״̬����ȫ");
                Printmatrix(work);
                for(int k=0;k<team.size();k++){
                    team.get(k).finshined=false;//�ѽ���״̬ȫ����ԭ
                }
                return false;
                }
            }

        System.out.println("��ǰ״̬��ȫ,��ȫ����Ϊ:");
        PrinSafe(safeteam);
		
		//����needֱ�ӷ�����ɣ�����Դ���ո�avaliable��
               boolean chice=false;
	    for(int j=0;j<team.size();j++){
               for(int i=0;i<KINDS;i++){
               if(team.get(j).needs[i] !=0){
				   chice=false;
                   break;
               }else {
                   chice=true;
               }
           }
               if(chice){
                   for(int l=0;l<KINDS;l++){
                       avaliable[l]+=team.get(j).allocation[l];
                       team.get(j).allocation[l]=0;
                   }
				   for(int k=0;k<team.size();k++){
                   team.get(k).finshined=false;//�ѽ���״̬ȫ����ԭ
                   }
		           return true;
				  
               }
		}
		for(int k=0;k<team.size();k++){
                   team.get(k).finshined=false;//�ѽ���״̬ȫ����ԭ
                   }
		return true;
        
    }
    private static void PrinSafe(String[] Safe){
        //�����ȫ����
        for(int i=0;i<Safe.length;i++){
            if(i==0){
                System.out.print("[");
            }
            if(i!=Safe.length-1) {
                System.out.print(Safe[i] + "��");
            }else {
                System.out.print(Safe[i]+"]");
            }
        }
        System.out.println();
        int[] work=new int[KINDS];
        for(int i=0;i<Safe.length;i++){
            for(int j=0;j<team.size();j++){
                if(Safe[i].equals(team.get(j).name)){
                    if(i==0){//��һ���Ļ����ǰ�avaliable+��һ�����̵�allocation
                        System.out.println("��ǰ������Դ��:"+Arrays.toString(avaliable));
                        for(int k=0;k<KINDS;k++){
                            work[k]=team.get(j).allocation[k]+avaliable[k];
                        }

                        System.out.println(team.get(j));//��ʼ��work�ĳ�ֵ
                        System.out.println("��ǰ������Դ��Ϊ"+Arrays.toString(work));
                        break;
                    }else{
                        System.out.println(team.get(j));
                        for(int k=0;k<KINDS;k++){
                            work[k]=team.get(j).allocation[k]+work[k];
                        }
                        System.out.println("��ǰ������Դ��Ϊ"+Arrays.toString(work));
                        break;
                    }
                }
            }
        }
        System.out.println();


    }
    public static void Printmatrix(){

        //�޲�����ʱ�򣬾�����ʾ��ǰ�Ľ�����Ϣ;
        if(team == null){
            System.out.println("���ʼ��������Դ��Ϣ!");
            return ;
        }
        System.out.println("��Դ����"+Arrays.toString(resource));
        System.out.println("��ǰ������Դ��"+Arrays.toString(avaliable));
        for(int i=0;i<team.size();i++){
        System.out.println(team.get(i));
    }
    }
    public static void Printmatrix(int[] work){
        //�в�����ʱ�򣬾�����ʾ��ǰ���õ���Դ�����͸����������е����4
        if(team == null){
            System.out.println("���ʼ��������Դ��Ϣ!");
            return ;
        }
        System.out.println("��Դ����"+Arrays.toString(work));
        System.out.println("��ǰ������Դ��"+Arrays.toString(avaliable));
        for(int i=0;i<team.size();i++){
            System.out.println(team.get(i));
        }
    }
    public static  void Blank(){
        if(team == null){
            System.out.println("���ʼ��������Դ��Ϣ!");
            return ;
        }
        Scanner scanner=new Scanner(System.in);
            System.out.println("��������Ҫ�����������");
            String name=scanner.next();
            for(int i=0;i<team.size();i++){
                if(team.get(i).name.equals(name)){

                    int request[]=new int[KINDS];
                    for(int j=0;j<KINDS;j++){
                        System.out.println("������Ҫ����ĵ�"+j+"����Դ��");
                        request[j]=scanner.nextInt();//����request��ֵ
                    }
                    for(int j=0;j<KINDS;j++){
                        //�Ƿ���ڿ�������
                        if(team.get(i).needs[j]<request[j]){
                            System.out.println("���������������ڽ�������");
                            return;
                        }
                    }
                    for(int j=0;j<KINDS;j++){
                        //�Ƿ���ڵ�ǰ���õ���Դ��
                        if(avaliable[j]<request[j]){
                            System.out.println("���������������ڿ��Է�����Դ");
                            return;
                        }
                    }//ǰ���ֶ�ͨ��
                    TryAllcotion(i,request);//iΪ���̵�ID��requestΪ������Դ��
					return;
                }
            }
            System.out.println("��˶Ժ���������м��");
        }
	private static void TryAllcotion(int i,int[] request){
         for(int j=0;j<KINDS;j++){
             //�������ʱ�����i���̣��޸���need��allocation���޸ĵ�ǰ״̬������Դ��
		 team.get(i).allocation[j]+=request[j];
		 team.get(i).needs[j]-=request[j];
		 avaliable[j]-=request[j];
        }
		if(!SafeCheck()){//��ȫ�Լ��,����ȫ�Ļ�����ԭ�ղŷ�������
			System.out.println("״̬����ȫ��δ����");
			for(int j=0;j<KINDS;j++){
			team.get(i).allocation[j]-=request[j];
			team.get(i).needs[j]+=request[j];
			avaliable[j]+=request[j];
			}
			return;
		}
		System.out.println("״̬��ȫ���ѷ���");
	}
    public static void menu(){
        Scanner sc=new Scanner(System.in);
        int choice=1;
        while(choice != 0){
        System.out.println("********************************");
        System.out.println("*     1.��ʼ����Դ����          *");
        System.out.println("*     2.������Դ����            *");
        System.out.println("*     3.��ȫ�Լ��              *");
        System.out.println("*     4.���м��㷨              *");
        System.out.println("*     5.��ʾ����״̬            *");
        System.out.println("*     0.�˳�                    *");
        System.out.println("********************************");
        System.out.println("���������ѡ��");
            choice=sc.nextInt();
            if(choice == 0){
                return;
            }else if(choice == 1){
                InitAllResource();//��ʼ����Դ�������͸�����Դ�������
            }else if(choice == 2){
                InitProcess();//��ʼ����������
                setAvaliable();//��ʼ���ɷ�����Դ��
            }else if(choice ==3){
                SafeCheck();
            } else if(choice == 4){
				Blank();
			}else if(choice == 5){
                Printmatrix();
            }else {
                System.out.println("��������������������");
            }


        }


    }
    public static void main(String[] args) {
        menu();
    }
}
