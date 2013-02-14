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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Backupimg extends Activity{

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.backup);
	    setTitle(R.string.title_activity_Backupimg);
	    SharedPreferences Partition = getSharedPreferences("Partition", 0);
		final String KD = Partition.getString("Kernel", "mmcblk0p8");
		final String RD = Partition.getString("Recovery", "mmcblk0p17");
		final String ED = Partition.getString("EFS", "mmcblk0p10");
		
		String dirPath = Environment.getExternalStorageDirectory().getPath()+"/Backupimg";
		File file = new File(dirPath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		ImageButton KB = (ImageButton) findViewById(R.id.KB);
        KB.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
            	String path=Environment.getExternalStorageDirectory().getPath()+"/Backupimg/Kernel.img";
        	    File Kernel = new File(path);
        	    if(Kernel.exists()==true){
        	    	new AlertDialog.Builder(Backupimg.this)
        			.setIcon(R.drawable.pa_backup_small)
        			.setTitle("Ŀ�� ���")
        			.setMessage("���� Ŀ���� ����Ǿ� �ֽ��ϴ�.\n�ٽ� Ŀ���� ����Ͽ� �����ðڽ��ϱ�?")
        			.setPositiveButton("���", new DialogInterface.OnClickListener(){
        				public void onClick(DialogInterface dialog, int which){
        					try{
        						Runtime.getRuntime().exec("su");
        						try{
        			            	String[] backupkernel ={"su","-c","dd if=/dev/block/" + KD + " of=" + Environment.getExternalStorageDirectory().getPath() + "/Backupimg/Kernel.img"};
        			            	Runtime.getRuntime().exec(backupkernel);
        			            }catch(Exception a){
        			            	a.printStackTrace();
        			            }
        					}catch(Exception e){
        						Toast.makeText(Backupimg.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
        					}
        				}
        			})
        			.setNegativeButton("���", null)
        			.show();
        	    }else{
        	    	new AlertDialog.Builder(Backupimg.this)
        			.setIcon(R.drawable.pa_backup_small)
        			.setTitle("Ŀ�� ���")
        			.setMessage("���� Ŀ���� ����Ͻðڽ��ϱ�?")
        			.setPositiveButton("���", new DialogInterface.OnClickListener(){
        				public void onClick(DialogInterface dialog, int which){
        					try{
        						Runtime.getRuntime().exec("su");
        						try{
        			            	String[] backupkernel ={"su","-c","dd if=/dev/block/" + KD + " of=" + Environment.getExternalStorageDirectory().getPath() + "/Backupimg/Kernel.img"};
        			            	Runtime.getRuntime().exec(backupkernel);
        			            	}catch(Exception a){
        			            		a.printStackTrace();
        			            	}
        					}catch(Exception e){
        						Toast.makeText(Backupimg.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
        					}
        				}
        			})
        			.setNegativeButton("���", null)
        			.show();
        	    }
            }
        });
	    
        ImageButton KR = (ImageButton) findViewById(R.id.KR);
        KR.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
            	String path=Environment.getExternalStorageDirectory().getPath() + "/Backupimg/Kernel.img";
        	    File Kernel = new File(path);
        	    if(Kernel.exists()==true){
        	    	new AlertDialog.Builder(Backupimg.this)
        			.setIcon(R.drawable.pa_backup_small)
        			.setTitle("Ŀ�� �������")
        			.setMessage("����� Ŀ���� ������� �Ͻðڽ��ϱ�?")
        			.setPositiveButton("�������", new DialogInterface.OnClickListener(){
        				public void onClick(DialogInterface dialog, int which){
        					try{
        						Runtime.getRuntime().exec("su");
        						try{
        			            	String[] backuprecovery ={"su","-c","dd if=" + Environment.getExternalStorageDirectory().getPath() + "/Backupimg/Kernel.img of=/dev/block/" + KD};
        			            	Runtime.getRuntime().exec(backuprecovery);
        			            }catch(Exception a){
        			            	a.printStackTrace();
        			            }
        						new AlertDialog.Builder(Backupimg.this)
    							.setIcon(R.drawable.pa_backup_small)
    							.setTitle(R.string.title_activity_Backupimg)
    							.setMessage("����� �Ͻðڽ��ϱ�?")
    							.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener(){
    								public void onClick(DialogInterface dialog, int which){
    									try{
    		    		            		Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "reboot" });
    		    		                    proc.waitFor();
    		    		            	}catch (Exception b){
    		    		            		b.printStackTrace();
    		    		            	}
    								}
    							})
    							.setNegativeButton("���", null)
    							.show();
        					}catch(Exception e){
        						Toast.makeText(Backupimg.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
        					}
        				}
        			})
        			.setNegativeButton("���", null)
        			.show();
        	    }else{
        	    	Toast.makeText(Backupimg.this,"Ŀ�� ����� �Ǿ����� �ʽ��ϴ�.", Toast.LENGTH_SHORT).show();
        	    }
            }
        });
	    
        ImageButton RB = (ImageButton) findViewById(R.id.RB);
        RB.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
            	String recoverypath=Environment.getExternalStorageDirectory().getPath() + "/Backupimg/Recovery.img";
        	    File Recovery = new File(recoverypath);
        	    if(Recovery.exists()==true){
        	    	new AlertDialog.Builder(Backupimg.this)
        			.setIcon(R.drawable.pa_backup_small)
        			.setTitle("��Ŀ���� ���")
        			.setMessage("���� ��Ŀ������ ����Ǿ� �ֽ��ϴ�.\n�ٽ� ��Ŀ������ ����Ͽ� �����ðڽ��ϱ�?")
        			.setPositiveButton("���", new DialogInterface.OnClickListener(){
        				public void onClick(DialogInterface dialog, int which){
        					try{
        						Runtime.getRuntime().exec("su");
        						try{
        			            	String[] backuprecovery ={"su","-c","dd if=/dev/block/" + RD + " of=" + Environment.getExternalStorageDirectory().getPath() + "/Backupimg/Recovery.img"};
        			            	Runtime.getRuntime().exec(backuprecovery);
        			            }catch(Exception a){
        			            	a.printStackTrace();
        			            }
        					}catch(Exception e){
        						Toast.makeText(Backupimg.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
        					}
        				}
        			})
        			.setNegativeButton("���", null)
        			.show();
        	    }else{
        	    	new AlertDialog.Builder(Backupimg.this)
        			.setIcon(R.drawable.pa_backup_small)
        			.setTitle("��Ŀ���� ���")
        			.setMessage("���� ��Ŀ������ ����Ͻðڽ��ϱ�?")
        			.setPositiveButton("���", new DialogInterface.OnClickListener(){
        				public void onClick(DialogInterface dialog, int which){
        					try{
        						Runtime.getRuntime().exec("su");
        						try{
        			            	String[] backuprecovery ={"su","-c","dd if=/dev/block/" + RD + " of=" + Environment.getExternalStorageDirectory().getPath() + "/Backupimg/Recovery.img"};
        			            	Runtime.getRuntime().exec(backuprecovery);
        			            }catch(Exception a){
        			            	a.printStackTrace();
        			            }
        					}catch(Exception e){
        						Toast.makeText(Backupimg.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
        					}
        				}
        			})
        			.setNegativeButton("���", null)
        			.show();
        	    }
            }
        });
        
        ImageButton RR = (ImageButton) findViewById(R.id.RR);
        RR.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
            	String recoverypath=Environment.getExternalStorageDirectory().getPath()+"/Backupimg/Recovery.img";
        	    File Recovery = new File(recoverypath);
        	    if(Recovery.exists()==true){
        	    	new AlertDialog.Builder(Backupimg.this)
        			.setIcon(R.drawable.pa_backup_small)
        			.setTitle("��Ŀ���� �������")
        			.setMessage("����� ��Ŀ������ ������� �Ͻðڽ��ϱ�?")
        			.setPositiveButton("�������", new DialogInterface.OnClickListener(){
        				public void onClick(DialogInterface dialog, int which){
        					try{
        						Runtime.getRuntime().exec("su");
        						try{
        			            	String[] backuprecovery ={"su","-c","dd if=" + Environment.getExternalStorageDirectory().getPath() + "/Backupimg/Recovery.img of=/dev/block/" + RD};
        			            	Runtime.getRuntime().exec(backuprecovery);
        			            }catch(Exception a){
        			            	a.printStackTrace();
        			            }
        					}catch(Exception e){
        						Toast.makeText(Backupimg.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
        					}
        				}
        			})
        			.setNegativeButton("���", null)
        			.show();
        	    }else{
        	    	Toast.makeText(Backupimg.this,"��Ŀ���� ����� �Ǿ����� �ʽ��ϴ�.", Toast.LENGTH_SHORT).show();
        	    }
            }
        });
	
        ImageButton EB = (ImageButton) findViewById(R.id.EB);
	    EB.setOnClickListener(new OnClickListener(){
	        public void onClick(View v){
	        	String efspath=Environment.getExternalStorageDirectory().getPath() + "/Backupimg/EFS.img";
	    	    File EFS = new File(efspath);
	    	    if(EFS.exists()==true){
	    	    	new AlertDialog.Builder(Backupimg.this)
	    			.setIcon(R.drawable.pa_backup_small)
	    			.setTitle("EFS ���")
	    			.setMessage("���� EFS�� ����Ǿ� �ֽ��ϴ�.\n�ٽ� EFS�� ����Ͽ� �����ðڽ��ϱ�?")
	    			.setPositiveButton("���", new DialogInterface.OnClickListener(){
	    				public void onClick(DialogInterface dialog, int which){
	    					try{
	    						Runtime.getRuntime().exec("su");
	    						try{
	    			            	String[] backuprecovery ={"su","-c","dd if=/dev/block/" + ED + " of=" + Environment.getExternalStorageDirectory().getPath() + "/Backupimg/EFS.img"};
	    			            	Runtime.getRuntime().exec(backuprecovery);
	    			            }catch(Exception a){
	    			            	a.printStackTrace();
	    			            }
	    					}catch(Exception e){
	    						Toast.makeText(Backupimg.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
	    					}
	    				}
	    			})
	    			.setNegativeButton("���", null)
	    			.show();
	    	    }else{
	    	    	new AlertDialog.Builder(Backupimg.this)
	    			.setIcon(R.drawable.pa_backup_small)
	    			.setTitle("EFS ���")
	    			.setMessage("���� EFS�� ����Ͻðڽ��ϱ�?")
	    			.setPositiveButton("���", new DialogInterface.OnClickListener(){
	    				public void onClick(DialogInterface dialog, int which){
	    					try{
	    						Runtime.getRuntime().exec("su");
	    						try{
	    			            	String[] backuprecovery ={"su","-c","dd if=/dev/block/" + ED + " of=" + Environment.getExternalStorageDirectory().getPath() + "/Backupimg/EFS.img"};
	    			            	Runtime.getRuntime().exec(backuprecovery);
	    			            }catch(Exception a){
	    			            	a.printStackTrace();
	    			            }
	    					}catch(Exception e){
	    						Toast.makeText(Backupimg.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
	    					}
	    				}
	    			})
	    			.setNegativeButton("���", null)
	    			.show();
	    	    }
	        }
	    });
	    
	    ImageButton ER = (ImageButton) findViewById(R.id.ER);
	    ER.setOnClickListener(new OnClickListener(){
	        public void onClick(View v){
	        	String efspath=Environment.getExternalStorageDirectory().getPath()+"/Backupimg/EFS.img";
	    	    File EFS = new File(efspath);
	    	    if(EFS.exists()==true){
	    	    	new AlertDialog.Builder(Backupimg.this)
	    			.setIcon(R.drawable.pa_backup_small)
	    			.setTitle("EFS �������")
	    			.setMessage("����� EFS�� ������� �Ͻðڽ��ϱ�?")
	    			.setPositiveButton("�������", new DialogInterface.OnClickListener(){
	    				public void onClick(DialogInterface dialog, int which){
	    					try{
	    						Runtime.getRuntime().exec("su");
	    						try{
	    			            	String[] backuprecovery ={"su","-c","dd if=" + Environment.getExternalStorageDirectory().getPath() + "/Backupimg/EFS.img of=/dev/block/" + ED};
	    			            	Runtime.getRuntime().exec(backuprecovery);
	    			            }catch(Exception a){
	    			            	a.printStackTrace();
	    			            }
	    					}catch(Exception e){
	    						Toast.makeText(Backupimg.this,"�� ����� ����Ϸ��� ������ �ʿ��մϴ�.", Toast.LENGTH_SHORT).show();
	    					}
	    				}
	    			})
	    			.setNegativeButton("���", null)
	    			.show();
	    	    }else{
	    	    	Toast.makeText(Backupimg.this,"EFS ����� �Ǿ����� �ʽ��ϴ�.", Toast.LENGTH_SHORT).show();
	    	    }
	        }
	    });
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.pa_backup_small)
			.setTitle(R.string.title_activity_Backupimg)
			.setMessage("�޴��� ���ư��ðڽ��ϱ�?")
			.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					Backupimg.this.finish();
					overridePendingTransition(R.anim.rightin, R.anim.rightout);
				}
			})
			.setNegativeButton("���", null)
			.show();
		}
		return true;
	}
}