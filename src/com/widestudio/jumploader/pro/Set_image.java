/*
 * Copyright 2013 WideStudio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.widestudio.jumploader.pro;

import java.io.File;
import java.util.*;

import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Set_image extends ListActivity{
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_img);
        setTitle(R.string.title_activity_Set_image);
        myPath = (TextView)findViewById(R.id.path);
        root = Environment.getExternalStorageDirectory().getPath();
        getDir(root);
    }
    
    private void getDir(String dirPath){
    	myPath.setText("���丮 : " + dirPath +"/");
    	item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
		//Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
    	//Arrays.sort(files);
    	List<File> list = Arrays.asList(files);
    	Collections.sort(list);
    	
    	if(!dirPath.equals(root)){
    		item.add(root + "/");
    		path.add(root);
    		item.add("../");
    		path.add(f.getParent());	
    	}
    	
    	for(int i=0; i < files.length; i++){
    		File file = files[i];
    		if(!file.isHidden() && file.canRead()){
    			path.add(file.getPath());
        		if(file.isDirectory()){
        			item.add(file.getName() + "/");
        		}else{
        			item.add(file.getName());
        		}
    		}	
    	}
    	ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, R.layout.list, item);
    	setListAdapter(fileList);	
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    }
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		final File file = new File(path.get(position));
		if(file.isDirectory()){
			if(file.canRead()){
				getDir(path.get(position));
			}else{
				new AlertDialog.Builder(this)
					.setIcon(R.drawable.flashing_small)
					.setTitle(R.string.title_activity_Set_image)
					.setMessage(file.getName() + "��(��) �� ���� �����ϴ�.")
					.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
					}
				}).show();
			}
		}else{
			SharedPreferences Partition = getSharedPreferences("Partition", 0);
			final String KD = Partition.getString("Kernel", "mmcblk0p8");
			final String RD = Partition.getString("Recovery", "mmcblk0p17");
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.flashing_small)
			.setTitle(R.string.title_activity_Set_image)
			.setMessage(file.getName()+"��(��) �÷��� �Ͻðڽ��ϱ�?")
			.setPositiveButton("Ŀ��", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					new AlertDialog.Builder(Set_image.this)
					.setIcon(R.drawable.flashing_small)
					.setTitle("Ŀ��")
					.setMessage(file.getName()+"��(��) Ŀ�η� �÷��� �Ͻðڽ��ϱ�?")
					.setPositiveButton("Ȯ��", new OnClickListener(){
						public void onClick(DialogInterface dialog, int which){
							try{
								Runtime.getRuntime().exec("su");
								try{
					            	String[] flashrecovery ={"su","-c","dd if=" + file.getPath() + " of=/dev/block/" + KD};
					            	Runtime.getRuntime().exec(flashrecovery);
					            }catch(Exception a){
					            	a.printStackTrace();
					            }
								new AlertDialog.Builder(Set_image.this)
								.setIcon(R.drawable.flashing_small)
								.setTitle(R.string.title_activity_Set_image)
								.setMessage("����� �Ͻðڽ��ϱ�?")
								.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener(){
									public void onClick(DialogInterface dialog, int which){
										try{
				    		            	Process proc = Runtime.getRuntime().exec(new String[] {"su", "-c", "reboot"});
				    		            	proc.waitFor();
				    		            }catch(Exception g){
				    		            	g.printStackTrace();
				    		            }
									}
								})
								.setNegativeButton("���", null)
								.show();
							}catch(Exception d){
								Toast.makeText(Set_image.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
							}
						}
					})
					.setNegativeButton("���", null)
					.show();
				}
			})
			.setNeutralButton("���", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					new AlertDialog.Builder(Set_image.this)
					.setIcon(R.drawable.flashing_small)
					.setTitle("���")
					.setMessage(file.getName()+"��(��) ���� �÷��� �Ͻðڽ��ϱ�?")
					.setPositiveButton("Ȯ��", new OnClickListener(){
						public void onClick(DialogInterface dialog, int which){
							try{
								try{
									String[] flashmoddules ={"su","-c","dd if=" + file.getPath() + " of=/system/lib/modules/" + file.getName() + " && chmod 644 /system/lib/modules/" + file.getName()};
									Runtime.getRuntime().exec(flashmoddules);
								}catch(Exception b){
					            	b.printStackTrace();
					            }
							}catch(Exception e){
								Toast.makeText(Set_image.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
							}
						}
					})
					.setNegativeButton("���", null)
					.show();
				}
			})
			.setNegativeButton("��Ŀ����", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					new AlertDialog.Builder(Set_image.this)
					.setIcon(R.drawable.flashing_small)
					.setTitle("��Ŀ����")
					.setMessage(file.getName()+"��(��) ��Ŀ������ �÷��� �Ͻðڽ��ϱ�?")
					.setPositiveButton("Ȯ��", new OnClickListener(){
						public void onClick(DialogInterface dialog, int which){
							try{
								Runtime.getRuntime().exec("su");
								try{
									String[] flashrecovery ={"su","-c","dd if=" + file.getPath() + " of=/dev/block/" + RD};
									Runtime.getRuntime().exec(flashrecovery);
								}catch(Exception c){
					            	c.printStackTrace();
					            }
							}catch(Exception f){
								Toast.makeText(Set_image.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
							}
						}
					})
					.setNegativeButton("���", null)
					.show();
				}
			})
			.show();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.flashing_small)
			.setTitle(R.string.title_activity_Set_image)
			.setMessage("�޴��� ���ư��ðڽ��ϱ�?")
			.setPositiveButton("Ȯ��", new OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					Set_image.this.finish();
					overridePendingTransition(R.anim.rightin, R.anim.rightout);
				}
			})
			.setNegativeButton("���", null)
			.show();
		}
		return true;
	}
}